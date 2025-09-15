package com.synergisticit.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.synergisticit.domain.Account;
import com.synergisticit.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

	@Override
	public List<Account> findAll() {
		return accountRepository.findAll();
	}

	@Override
	public Account saveAccount(Account account) {
		return accountRepository.save(account);
	}

	@Override
	public Account findAccountById(Long id) {
		return accountRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteAccountById(Long id) {
		accountRepository.deleteById(id);
	}

	@Override
	public List<Account> findByCustomerId(Long customerId) {
		return accountRepository.findByAccountCustomer_CustomerId(customerId);
	}

}
