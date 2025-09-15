package com.synergisticit.domain;

import java.time.LocalDateTime;

import com.synergisticit.auditing.Auditable;
import com.synergisticit.enums.BankTransactionType;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter 
@Setter
@Entity
public class BankTransactions extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BankTransactionType type;  

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private LocalDateTime occurredAt;

    @ManyToOne
    @JoinColumn(name = "from_account_id")
    private Account fromAccount;       

    @ManyToOne
    @JoinColumn(name = "to_account_id")
    private Account toAccount;       

    private String comments;

    @PrePersist
    void prePersist() {
        if (occurredAt == null) occurredAt = LocalDateTime.now();
    }
}

