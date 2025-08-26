package com.synergisticit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synergisticit.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	Account findByAccountId(Long accountId);
	
}
