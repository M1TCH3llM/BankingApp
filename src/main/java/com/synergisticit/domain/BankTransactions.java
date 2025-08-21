package com.synergisticit.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.synergisticit.auditing.Auditable;
import com.synergisticit.enums.BankTransactionType;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
public class BankTransactions extends Auditable {

		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private Long bankTransactionId;
		
		
		private Long bankTransactionFromAccount;
		
		
		private Long bankTransactionToAccount;

		private BankTransactionType bankTransactionType;
		
		private LocalDateTime bankTransactionDate;
		
		private String comments;
		

}
