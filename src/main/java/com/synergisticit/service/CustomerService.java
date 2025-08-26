package com.synergisticit.service;

import java.util.List;

import com.synergisticit.domain.Customer;

public interface CustomerService {
	
	Customer findByCustomerName(String customerName);
	Customer saveCustomer(Customer customer);
	Customer findCustomerById(Long customerId);
	List<Customer> findAll();
	Customer updateCustomerById(Long customerId, Customer updates);
	Customer deleteCustomerById(Long customerId);
}
