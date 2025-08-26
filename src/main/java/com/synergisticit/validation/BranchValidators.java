package com.synergisticit.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.synergisticit.domain.Branch;

@Component
public class BranchValidators implements Validator{
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Branch.class.equals(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
	    Branch b = (Branch) target;

	    // Branch Name
	    ValidationUtils.rejectIfEmptyOrWhitespace(
	        errors, "branchName", "branchName.empty", "***Branch name should not be left empty."
	    );

	    // Address fields
	    if (b.getBranchAddress() == null) {
	        errors.rejectValue("branchAddress", "branchAddress.null", "***Address is required.");
	    } else {
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
	                "branchAddress.addressLine1", "addressLine1.empty", "***Address Line 1 should not be empty.");

	        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
	                "branchAddress.city", "city.empty", "***City should not be empty.");

	        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
	                "branchAddress.state", "state.empty", "***State should not be empty.");

	        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
	                "branchAddress.zip", "zip.empty", "***ZIP should not be empty.");

	        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
	                "branchAddress.country", "country.empty", "***Country should not be empty.");
	    }
	}

	
}