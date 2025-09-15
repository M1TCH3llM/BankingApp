package com.synergisticit.service;

import java.util.List;

import com.synergisticit.domain.BankTransactions;

public interface BankTransactionService {
    BankTransactions log(BankTransactions tx);
    List<BankTransactions> recentForAccount(Long accountId, int limit);
}
