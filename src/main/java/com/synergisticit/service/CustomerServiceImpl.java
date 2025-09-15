package com.synergisticit.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.synergisticit.domain.Account;
import com.synergisticit.domain.Branch;
import com.synergisticit.domain.Customer;
import com.synergisticit.enums.AccountType;
import com.synergisticit.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService  {
		
	private final CustomerRepository customerRepository;
	private final AccountService accountService;
	private final BranchService branchService;

	public CustomerServiceImpl(CustomerRepository customerRepository, 
								AccountService accountService,
								BranchService branchService) {
		this.customerRepository = customerRepository;
		this.accountService = accountService;
        this.branchService = branchService;
	}

	@Override
	public Customer findByCustomerName(String customerName) {
		return customerRepository.findByCustomerName(customerName);
	}

	@Override
	public Customer saveCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public Customer findCustomerById(Long customerId) {
		return customerRepository.findById(customerId).orElse(null);
	}

	@Override
	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	@Override
	public Customer updateCustomerById(Long customerId, Customer updates) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer deleteCustomerById(Long customerId) {
		Customer existing = customerRepository.findById(customerId).orElse(null);
	    if (existing != null) {
	        customerRepository.deleteById(customerId);
	    }
	    return existing;
	}

	@Override
	public Customer saveCustomerAndOpenAccount(Customer customer, 
												AccountType initialAccountType, 
												Long branchId,
												Double openingBalance) {
		
		if (initialAccountType != null && 
				(customer.getCustomerAccounts() == null || !customer.getCustomerAccounts().contains(initialAccountType))) {
				customer.getCustomerAccounts().add(initialAccountType);
		}
		
		Customer saved = customerRepository.save(customer);
		
		Branch branch = null;
		if (branchId != null) {
			branch = branchService.findBranchById(branchId); 
		}
		
		Account acc = new Account();
		acc.setAccountCustomer(saved);
        acc.setAccountType(initialAccountType);                
        acc.setAccountDateOpened(LocalDate.now());
        acc.setAccountBalance(openingBalance != null ? openingBalance : 0.0);
        acc.setAccountHolder(saved.getCustomerName());          
        acc.setAccountBranch(branch);                         

        accountService.saveAccount(acc);

        return saved;

	}

	@Override
	public Customer findByUserUsername(String name) {
		 return customerRepository.findByUser_Username(name);	
		 }

		
		
}
