package com.synergisticit.service;

import java.util.List;

import com.synergisticit.domain.Account;

public interface AccountService {

		List<Account> findAll();
		Account saveAccount(Account account);
		Account findAccountById(Long id);
		void deleteAccountById(Long id);
}
