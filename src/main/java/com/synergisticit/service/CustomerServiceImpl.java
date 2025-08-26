package com.synergisticit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.synergisticit.domain.Customer;
import com.synergisticit.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService  {
		
	private final CustomerRepository customerRepository;

	public CustomerServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
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
		// TODO Auto-generated method stub
		return null;
	}

	
		
		
}
