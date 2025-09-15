package com.synergisticit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synergisticit.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
	
	Customer findByCustomerName(String customerName);
	Customer findByUser_Username(String username);


}
