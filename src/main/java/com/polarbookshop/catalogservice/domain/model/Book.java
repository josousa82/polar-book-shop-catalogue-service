package com.polarbookshop.catalogservice.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.Date;


// TODO 1. Move the validation logic to a DTO, if additional logic is passed here implement
//  a command pattern or converter and sanitise before passing the request to the service and
//  convert to DTO before returning as a response. it's a bad practice return the entity as
//  response.
public record Book(
        @Id
        Long id,

        @NotBlank(message = "The book ISBN must be defined.")
        @Pattern(regexp = "^([0-9]{10}|[0-13]{13})$", message = "The ISBN format must be valid.")
        String isbn,

        @NotBlank(message = "The book title must be defined.")
        String title,

        @NotBlank(message = "The book author must be defined.")
        String author,

        @NotNull(message = "The book price must be defined.")
        @Positive(message = "The book price must be greater than zero.")
        Double price,

        @CreatedDate
        Instant createdDate,

        @LastModifiedBy
        Instant lastModifiedDate,

        @Version
        int version
) {
    public static Book of(String isbn, String title, String author, Double price) {
            var now = Instant.now();
            return new Book(
                null,
                isbn,
                title,
                author,
                price,
                    now,
                    now,
                0);
    }
}
