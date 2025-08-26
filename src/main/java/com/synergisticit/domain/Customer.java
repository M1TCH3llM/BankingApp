package com.synergisticit.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.synergisticit.auditing.Auditable;
import com.synergisticit.enums.AccountType;
import com.synergisticit.enums.Gender;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Customer extends Auditable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long customerId;
	
	private String customerName;
	
	private Gender customerGender;
	
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
	private LocalDate customerDOB;
	
	@Embedded
	private Address customerAddress;
	
	private String customerSSN;
	
	@ElementCollection(targetClass = AccountType.class)
	@Enumerated(EnumType.STRING)
	private List<AccountType> customerAccounts = new ArrayList<>();

	
	@OneToOne
	private User user; 
	
	
}
