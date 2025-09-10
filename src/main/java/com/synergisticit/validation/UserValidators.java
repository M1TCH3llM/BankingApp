package com.synergisticit.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.synergisticit.domain.User;

@Component
public class UserValidators implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz); 
    }

    @Override
    public void validate(Object target, Errors errors) {
        User u = (User) target;

        // Username
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "username.empty", "***Username cannot be empty.");

        // Password
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.empty", "***Password cannot be empty.");
        if (u.getPassword() != null && u.getPassword().length() < 6) {
            errors.rejectValue("password", "password.tooShort", "***Password must be at least 6 characters.");
        }

        // Email
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.empty", "***Email cannot be empty.");
        if (u.getEmail() != null && !u.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errors.rejectValue("email", "email.invalid", "***Invalid email format.");
        }

        // Roles
        if (u.getRoles() == null || u.getRoles().isEmpty()) {
            errors.rejectValue("roles", "roles.empty", "***At least one role must be selected.");
        }
    }
}
