package com.synergisticit.domain;

import java.util.List;

import com.synergisticit.auditing.Auditable;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
public class Address {

	
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String country;
	private String zip;



}
