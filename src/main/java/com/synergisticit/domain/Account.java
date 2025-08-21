package com.synergisticit.domain;

import java.time.LocalDate;
import java.util.List;

import com.synergisticit.auditing.Auditable;
import com.synergisticit.enums.AccountType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Account extends Auditable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long accountId;
	
	@Enumerated(EnumType.STRING)
	private AccountType accountType;

	private String accountHolder;
	
	private LocalDate accountDateOpened;
	
	private double accountBalance;
	
	@ManyToOne // person and branch can have many accounts
	private Branch accountBranch;
	

	@ManyToOne
	@JoinColumn(name = "customerId")
	private Customer accountCustomer;
}
