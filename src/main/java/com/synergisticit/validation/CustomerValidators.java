package com.synergisticit.validation;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.synergisticit.domain.Customer;

@Component
public class CustomerValidators implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Customer.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Customer c = (Customer) target;

        // --- Name ---
        ValidationUtils.rejectIfEmptyOrWhitespace(
            errors, "customerName", "customerName.empty", "***Name should not be left empty."
        );

        // --- Gender ---
        if (c.getCustomerGender() == null) {
            errors.rejectValue("customerGender", "customerGender.empty", "***Please select a gender.");
        }

        // --- DOB ---
        if (c.getCustomerDOB() == null) {
            errors.rejectValue("customerDOB", "customerDOB.empty", "***Date of Birth is required.");
        } else {
            if (c.getCustomerDOB().isAfter(LocalDate.now())) {
                errors.rejectValue("customerDOB", "customerDOB.future", "***Date of Birth cannot be in the future.");
            }
            int age = Period.between(c.getCustomerDOB(), LocalDate.now()).getYears();
            if (age < 18) {
                errors.rejectValue("customerDOB", "customerDOB.tooYoung", "***Customer must be at least 18 years old.");
            }
        }

        // --- SSN ---
        ValidationUtils.rejectIfEmptyOrWhitespace(
            errors, "customerSSN", "customerSSN.empty", "***SSN cannot be empty."
        );
        if (c.getCustomerSSN() != null && 
            !c.getCustomerSSN().matches("^(?!000|666)[0-9]{3}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$")) {
            errors.rejectValue("customerSSN", "customerSSN.invalid", "***SSN must follow the format XXX-XX-XXXX.");
        }

        // --- Account Types ---
        boolean isNew = (c.getCustomerId() == null);
        if (!isNew) {
            if (c.getCustomerAccounts() == null || c.getCustomerAccounts().isEmpty()) {
                errors.rejectValue("customerAccounts", "accountTypes.required", "***Select at least one account type.");
            }
        }

        // --- Address ---
        if (c.getCustomerAddress() == null) {
            errors.rejectValue("customerAddress", "customerAddress.null", "***Address is required.");
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

