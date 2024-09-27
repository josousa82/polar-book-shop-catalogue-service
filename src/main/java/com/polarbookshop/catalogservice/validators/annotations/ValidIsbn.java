package com.polarbookshop.catalogservice.validators.annotations;

import com.polarbookshop.catalogservice.validators.IsbnValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.internal.constraintvalidators.hv.ISBNValidator;

import java.lang.annotation.*;

// NOTE: Naive validation of ISBN without checksum validation for practice and experimentation purposes

@Documented
@Constraint(validatedBy = IsbnValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@NotNull(message = "ISBN cannot be null.")
public @interface ValidIsbn {
    String message() default " Invalid ISBN format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
