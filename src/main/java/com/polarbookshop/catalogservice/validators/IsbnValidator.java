package com.polarbookshop.catalogservice.validators;

import com.polarbookshop.catalogservice.validators.annotations.ValidIsbn;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsbnValidator implements ConstraintValidator<ValidIsbn, String> {

    private static final String ISBN_REGEX = "^(\\d{10}|\\d{13})$";

    @Override
    public void initialize(ValidIsbn constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext constraintValidatorContext) {
        if(isbn == null) return false;
        return isbn.matches(ISBN_REGEX);
    }
}
