package org.example.bookstoremanagementsystem.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidation implements ConstraintValidator<ValidPhoneNumber, String> {
    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if(phoneNumber == null)
            return false;
        return phoneNumber.matches("^[0-9]{10}$");
    }
}
