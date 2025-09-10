package com.synergisticit.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.synergisticit.domain.Address;
import com.synergisticit.domain.Branch;
import com.synergisticit.domain.Customer;
import com.synergisticit.enums.AccountType;
import com.synergisticit.enums.Gender;
import com.synergisticit.service.BranchService;
import com.synergisticit.service.CustomerService;
import com.synergisticit.validation.CustomerValidators;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final BranchService branchService;
    private final CustomerValidators customerValidator;

    public CustomerController(CustomerService customerService,
                              BranchService branchService,
                              CustomerValidators customerValidator) {
        this.customerService = customerService;
        this.branchService = branchService;
        this.customerValidator = customerValidator;
    }

    // dropdown data
    @ModelAttribute("accountTypes")
    public AccountType[] accountTypes() { return AccountType.values(); }

    @ModelAttribute("genders")
    public Gender[] genders() { return Gender.values(); }

    @ModelAttribute("branches")
    public List<Branch> branches() {
        // If your BranchService only supports pageable:
        return branchService.findAll(Pageable.unpaged()).getContent();
        // If you also have non-paged findAll(), you can return that instead.
    }

    @GetMapping("/page")
    public String customerPage(@RequestParam(value = "editId", required = false) Long editId,
                               Model model) {
        Customer form = (editId != null) ? customerService.findCustomerById(editId) : new Customer();
        if (form == null) form = new Customer();
        if (form.getCustomerAddress() == null) form.setCustomerAddress(new Address());

        model.addAttribute("customer", form);
        model.addAttribute("customers", customerService.findAll());
        return "customerForm"; // list + form page (your existing JSP)
    }

    // Save customer. If NEW, also open an account and add the account type to the customer's list.
    @PostMapping("/page")
    public String createFromForm(@Valid @ModelAttribute("customer") Customer customer,
                                 BindingResult result,
                                 @RequestParam(value = "initialAccountType", required = false) AccountType initialAccountType,
                                 @RequestParam(value = "branchId", required = false) Long branchId,
                                 @RequestParam(value = "openingBalance", required = false) Double openingBalance,
                                 RedirectAttributes ra,
                                 Model model) {

        if (result.hasErrors()) {
            model.addAttribute("customers", customerService.findAll());
            return "customerForm";
        }

        boolean isNew = (customer.getCustomerId() == null);

        if (isNew) {
            // require an account type for brand-new customers
            if (initialAccountType == null) {
                result.rejectValue("customerAccounts", "accountType.required", "Please choose an initial account type.");
                model.addAttribute("customers", customerService.findAll());
                return "customerForm";
            }
            // Save + auto-create account atomically (service is @Transactional)
            customerService.saveCustomerAndOpenAccount(customer, initialAccountType, branchId, openingBalance);
            ra.addFlashAttribute("msg", "Customer created and account opened.");
        } else {
            // Editing existing customer: just save; do NOT auto-create another account
            customerService.saveCustomer(customer);
            ra.addFlashAttribute("msg", "Customer updated.");
        }

        return "redirect:/customer/page";
    }

    @PostMapping("/delete/{id:\\d+}")
    public String deleteCustomerPost(@PathVariable("id") Long id, RedirectAttributes ra) {
        customerService.deleteCustomerById(id);
        ra.addFlashAttribute("msg", "Customer deleted.");
        return "redirect:/customer/page";
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(customerValidator);
    }
}

