package com.synergisticit.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.synergisticit.domain.BankTransactions;
import com.synergisticit.repository.BankTransactionsRepository;

@Service
public class BankTransactionServiceImpl implements BankTransactionService {

    private final BankTransactionsRepository repo;

    public BankTransactionServiceImpl(BankTransactionsRepository repo) {
        this.repo = repo;
    }

    @Override
    public BankTransactions log(BankTransactions tx) {
        return repo.save(tx);
    }

    @Override
    public List<BankTransactions> recentForAccount(Long accountId, int limit) {
        return repo.findByFromAccount_AccountIdOrToAccount_AccountIdOrderByOccurredAtDesc(
                accountId, accountId, PageRequest.of(0, Math.max(1, limit))
        ).getContent();
    }
}
