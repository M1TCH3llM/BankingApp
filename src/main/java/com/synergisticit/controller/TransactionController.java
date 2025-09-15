package com.synergisticit.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.synergisticit.domain.Account;
import com.synergisticit.domain.Customer;
import com.synergisticit.domain.BankTransactions;
import com.synergisticit.enums.AccountType;
import com.synergisticit.enums.BankTransactionType;
import com.synergisticit.service.AccountService;
import com.synergisticit.service.BankTransactionService;
import com.synergisticit.service.CustomerService;

import jakarta.transaction.Transactional;

@Controller
@RequestMapping("/tx")
public class TransactionController {

    private final AccountService accountService;
    private final CustomerService customerService;
    private final BankTransactionService txService;


    public TransactionController(AccountService accountService, CustomerService customerService, BankTransactionService txService) {
        this.accountService = accountService;
        this.customerService = customerService;
        this.txService = txService;
    }
    
    private boolean isAdmin(Authentication auth) {
        return auth.getAuthorities().stream()
                .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));
    }
    
    private void requireOwnershipOrAdmin(Account account, Customer me, boolean admin) {
        if (admin) return;
        if (me == null || account == null || account.getAccountCustomer() == null
                || !account.getAccountCustomer().getCustomerId().equals(me.getCustomerId())) {
            throw new IllegalArgumentException("You can only operate on your own accounts.");
        }
    }

    private static void ensurePositive(double amount) {
        if (amount <= 0.0) throw new IllegalArgumentException("Amount must be greater than 0.");
    }

    @GetMapping("/page")
    public String page(Authentication auth, Model model) {
        model.addAttribute("isAdmin", true);
        model.addAttribute("allAccounts", accountService.findAll());
        return "txForm";
    }
    
    @PostMapping("/deposit")
    @Transactional
    public String deposit(@RequestParam("toAccountId") Long toAccountId,
                          @RequestParam("amount") double amount,
                          @RequestParam(value = "note", required = false) String note,
                          Authentication auth,
                          RedirectAttributes ra) {

        boolean admin = isAdmin(auth);
        Customer me = admin ? null : customerService.findByCustomerName(auth.getName());

        try {
            ensurePositive(amount);

            Account to = accountService.findAccountById(toAccountId);
            if (to == null) throw new IllegalArgumentException("Destination account not found.");

            // Users may only deposit into their own accounts
            requireOwnershipOrAdmin(to, me, admin);

            // Apply
            to.setAccountBalance(to.getAccountBalance() + amount);
            accountService.saveAccount(to);

            // Log
            BankTransactions tx = new BankTransactions();
            tx.setType(BankTransactionType.DEPOSIT);
            tx.setAmount(amount);
            tx.setToAccount(to);
            tx.setComments(note);
            txService.log(tx);

            ra.addFlashAttribute("msg", "Deposited $" + String.format("%.2f", amount) + " to account #" + toAccountId + ".");
        } catch (IllegalArgumentException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        } catch (Exception ex) {
            ra.addFlashAttribute("error", "Deposit failed.");
        }

        return "redirect:/tx/page";
    }
    
    @PostMapping("/withdraw")
    @Transactional
    public String withdraw(@RequestParam("fromAccountId") Long fromAccountId,
                           @RequestParam("amount") double amount,
                           @RequestParam(value = "note", required = false) String note,
                           Authentication auth,
                           RedirectAttributes ra) {

        boolean admin = isAdmin(auth);
        Customer me = admin ? null : customerService.findByCustomerName(auth.getName());

        try {
            ensurePositive(amount);

            Account from = accountService.findAccountById(fromAccountId);
            if (from == null) throw new IllegalArgumentException("Source account not found.");

            // Users may only withdraw from their own accounts
            requireOwnershipOrAdmin(from, me, admin);

            if (from.getAccountBalance() < amount) {
                throw new IllegalArgumentException("Insufficient funds.");
            }

            // Apply
            from.setAccountBalance(from.getAccountBalance() - amount);
            accountService.saveAccount(from);

            BankTransactions tx = new BankTransactions();
            tx.setType(BankTransactionType.WITHDRAWL);
            tx.setAmount(amount);
            tx.setFromAccount(from);
            tx.setComments(note);
            txService.log(tx);

            ra.addFlashAttribute("msg", "Withdrew $" + String.format("%.2f", amount) + " from account #" + fromAccountId + ".");
        } catch (IllegalArgumentException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        } catch (Exception ex) {
            ra.addFlashAttribute("error", "Withdrawal failed.");
        }

        return "redirect:/tx/page";
    }
    @PostMapping("/transfer")
    @Transactional
    public String transfer(@RequestParam("fromAccountId") Long fromAccountId,
                           @RequestParam("toAccountId") Long toAccountId,
                           @RequestParam("amount") double amount,
                           @RequestParam(value = "note", required = false) String note,
                           Authentication auth,
                           RedirectAttributes ra) {

        boolean admin = isAdmin(auth);
        Customer me = admin ? null : customerService.findByCustomerName(auth.getName());

        try {
            if (fromAccountId.equals(toAccountId)) {
                throw new IllegalArgumentException("Source and destination accounts must be different.");
            }
            ensurePositive(amount);

            Account from = accountService.findAccountById(fromAccountId);
            if (from == null) throw new IllegalArgumentException("Source account not found.");

            Account to = accountService.findAccountById(toAccountId);
            if (to == null) throw new IllegalArgumentException("Destination account not found.");

            // Users: source must be theirs; destination can be any valid account
            requireOwnershipOrAdmin(from, me, admin);

            if (from.getAccountBalance() < amount) {
                throw new IllegalArgumentException("Insufficient funds.");
            }

            // Apply
            from.setAccountBalance(from.getAccountBalance() - amount);
            to.setAccountBalance(to.getAccountBalance() + amount);
            accountService.saveAccount(from);
            accountService.saveAccount(to);

            // Log single transfer record (from & to)
            BankTransactions tx = new BankTransactions();
            tx.setType(BankTransactionType.TRANSFER);
            tx.setAmount(amount);
            tx.setFromAccount(from);
            tx.setToAccount(to);
            tx.setComments(note);
            txService.log(tx);

            ra.addFlashAttribute("msg",
                "Transferred $" + String.format("%.2f", amount) +
                " from #" + fromAccountId + " to #" + toAccountId + ".");
        } catch (IllegalArgumentException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        } catch (Exception ex) {
            ra.addFlashAttribute("error", "Transfer failed.");
        }

        return "redirect:/tx/page";
    }
}
