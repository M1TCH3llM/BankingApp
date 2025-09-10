package com.synergisticit.controller;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.synergisticit.domain.Account;
import com.synergisticit.domain.Branch;
import com.synergisticit.domain.Customer;
import com.synergisticit.enums.AccountType;
import com.synergisticit.service.AccountService;
import com.synergisticit.service.BranchService;
import com.synergisticit.service.CustomerService;



@Controller
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final BranchService branchService;
    private final CustomerService customerService;

    public AccountController(AccountService accountService,
                             BranchService branchService,
                             CustomerService customerService) {
        this.accountService = accountService;
        this.branchService = branchService;
        this.customerService = customerService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override public void setAsText(String text) {
                setValue((text == null || text.isBlank()) ? null : LocalDate.parse(text, fmt));
            }
        });
    }

    @ModelAttribute("accountTypes")
    public AccountType[] accountTypes() { return AccountType.values(); }

    @ModelAttribute("customers")
    public List<Customer> customers() {
        return customerService.findAll();
    }

    @ModelAttribute("branches")
    public List<Branch> branches() {
        return branchService.findAll(Pageable.unpaged()).getContent();
    }

    @GetMapping("/page")
    public String page(@RequestParam(value = "editId", required = false) Long editId,
                       @RequestParam(value = "customerId", required = false) Long presetCustomerId,
                       Model model) {

        model.addAttribute("accounts", accountService.findAll());

        Account form = (editId != null) ? accountService.findAccountById(editId) : new Account();
        if (form == null) form = new Account();
        if (form.getAccountDateOpened() == null) form.setAccountDateOpened(java.time.LocalDate.now());

        if (presetCustomerId != null) {
            Customer preset = customerService.findCustomerById(presetCustomerId);
            if (preset != null) {
                form.setAccountCustomer(preset);
                model.addAttribute("lockCustomer", true);
            }
            model.addAttribute("presetCustomerId", presetCustomerId);
        }

        model.addAttribute("account", form);
        return "accountPage";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("account") Account account,
                       @RequestParam("customerId") Long customerId,
                       @RequestParam("branchId") Long branchId,
                       RedirectAttributes ra) {
        Customer owner = customerService.findCustomerById(customerId);
        Branch branch = branchService.findBranchById(branchId);
        if (owner == null || branch == null) {
            ra.addFlashAttribute("error", "Customer/Branch is invalid.");
            return "redirect:/account/page";
        }

        account.setAccountCustomer(owner);
        account.setAccountBranch(branch);
        account.setAccountHolder(owner.getCustomerName()); 

        accountService.saveAccount(account);
//        ra.addFlashAttribute("msg", "Account saved.");
        return "redirect:/account/page";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        accountService.deleteAccountById(id);
        ra.addFlashAttribute("msg", "Account deleted.");
        return "redirect:/account/page";
    }
}


















