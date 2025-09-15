package com.synergisticit.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.synergisticit.domain.BankTransactions;

public interface BankTransactionsRepository extends JpaRepository<BankTransactions, Long> {

    Page<BankTransactions> findByFromAccount_AccountIdOrToAccount_AccountIdOrderByOccurredAtDesc(
            Long fromAccountId, Long toAccountId, Pageable pageable);
}
