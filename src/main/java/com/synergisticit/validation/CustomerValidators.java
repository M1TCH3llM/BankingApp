package com.synergisticit.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.synergisticit.domain.Customer;

@Component
public class CustomerValidators implements Validator{
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Customer.class.equals(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		Customer c = (Customer) target;
	
		 ValidationUtils.rejectIfEmptyOrWhitespace(
			        errors, "customerName", "customerName.empty", "***Customer name should not be left empty."
			    );
		 
		 // also need DOB, SSN, and Account type
		
		// Address fields
		    if (c.getCustomerAddress() == null) {
		        errors.rejectValue("customerAddress", "customerAddress.null", "***Customer is required.");
		    } else {
		        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
		                "customerAddress.addressLine1", "customerLine1.empty", "***Customer Line 1 should not be empty.");

		        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
		                "customerAddress.city", "city.empty", "***City should not be empty.");

		        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
		                "customerAddress.state", "state.empty", "***State should not be empty.");

		        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
		                "customerAddress.zip", "zip.empty", "***ZIP should not be empty.");

		        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
		                "customerAddress.country", "country.empty", "***Country should not be empty.");
		    }
		}
		 
	}
