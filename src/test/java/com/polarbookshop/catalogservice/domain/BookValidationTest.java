package com.polarbookshop.catalogservice.domain;

import com.polarbookshop.catalogservice.domain.model.Book;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BookValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsAreCorrectThenValidationSucceeds() {
        var book = Book.of("1234567890", "title", "author", 9.90);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertTrue(violations.isEmpty());
    }

    @Test
    void whenBookIsbnIsIncorrectThenValidationFails() {
        var book =  Book.of("123456789", "title", "author", 9.90);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertEquals("The ISBN format must be valid.", violations.iterator().next().getMessage());
    }

    @Test
    void whenBookTitleIsEmptyThenValidationFails() {
        var book =  Book.of("1234567890", "", "author", 9.90);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertEquals("The book title must be defined.", violations.iterator().next().getMessage());
    }

    @Test
    void whenBookAuthorIsEmptyThenValidationFails() {
        var book = Book.of("1234567890", "Title", "", 9.90);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertEquals("The book author must be defined.", violations.iterator().next().getMessage());
    }

    @Test
    void whenBookPriceIsZeroThenValidationFails() {
        var book =  Book.of("1234567890", "Title", "Author", 0.0);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertEquals("The book price must be greater than zero.", violations.iterator().next().getMessage());
    }

    @Test
    void whenBookPriceIsNullThenValidationFails() {
        var book =  Book.of("1234567890", "Title", "Author", null);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertEquals("The book price must be defined.", violations.iterator().next().getMessage());
    }
}