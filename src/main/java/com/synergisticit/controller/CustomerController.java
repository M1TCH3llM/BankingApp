package com.synergisticit.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.synergisticit.domain.Address;
import com.synergisticit.domain.Branch;
import com.synergisticit.domain.Customer;
import com.synergisticit.enums.AccountType;
import com.synergisticit.enums.Gender;
import com.synergisticit.service.AccountService;
import com.synergisticit.service.CustomerService;
import com.synergisticit.validation.CustomerValidators;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	private final CustomerService customerService;
	
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@Autowired
	private CustomerValidators customerValidator;
	
	@Autowired
	private AccountService accountService;

	 @GetMapping("/page")
	    public String customerPage(@RequestParam(value = "editId", required = false) Long editId,
	                             Model model) {
		 Customer form;

	        if (editId != null) {
	            form = customerService.findCustomerById(editId);
	            if (form == null) {
	                form = new Customer();
	            }
	        } else {
	            form = new Customer();
	        }

	        if (form.getCustomerAddress() == null) {
	            form.setCustomerAddress(new Address());
	        }

	        model.addAttribute("customer", form);
	        model.addAttribute("customers", customerService.findAll());
	        model.addAttribute("accounts", AccountType.values());
	        model.addAttribute("genders", Gender.values()); 
	        return "customerForm";
	    }

	    @PostMapping("/page")
	    public String createFromForm(@Valid @ModelAttribute("customer") Customer customer,
	                                 BindingResult result,
	                                 RedirectAttributes ra,
	                                 Model model) {
	        if (result.hasErrors()) {
	            model.addAttribute("customers", customerService.findAll());
	            return "customerForm"; // JSP shows <form:errors/>
	        }
	        boolean isUpdate = (customer.getCustomerId() != null);
	        customerService.saveCustomer(customer);
	        ra.addFlashAttribute("msg", isUpdate ? "Branch updated." : "Branch created.");
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
