package com.synergisticit.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.synergisticit.domain.Role;

@Component
public class RoleValidators implements Validator{
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Role.class.equals(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		Role r = (Role) target;

	    // Branch Name
	    ValidationUtils.rejectIfEmptyOrWhitespace(
	        errors, "roleName", "roleName.empty", "***Role name should not be left empty."
	    );
	}

	
}