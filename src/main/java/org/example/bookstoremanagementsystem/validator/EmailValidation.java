package org.example.bookstoremanagementsystem.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidation implements ConstraintValidator<ValidEmail, String> {
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null)
            return false;
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}
