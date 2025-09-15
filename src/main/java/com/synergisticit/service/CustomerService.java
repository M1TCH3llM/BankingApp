package com.synergisticit.service;

import java.util.List;

import com.synergisticit.domain.Customer;
import com.synergisticit.enums.AccountType;

public interface CustomerService {
	
	Customer findByCustomerName(String customerName);
	Customer saveCustomer(Customer customer);
	Customer findCustomerById(Long customerId);
	List<Customer> findAll();
	Customer updateCustomerById(Long customerId, Customer updates);
	Customer deleteCustomerById(Long customerId);
	
	Customer saveCustomerAndOpenAccount(Customer customer, 
										AccountType initialAccountType, 
										Long branchId, 
										Double openingBalance);
	Customer findByUserUsername(String name);
}
