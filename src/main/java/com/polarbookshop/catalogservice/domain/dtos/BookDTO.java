package com.polarbookshop.catalogservice.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class BookDTO {
    @NotBlank(message = "The book ISBN must be defined.")
    @Pattern(regexp = "^([0-9]{10}|[0-13]{13})$", message = "The ISBN format must be valid.")
    String isbn;

    @NotBlank(message = "The book title must be defined.")
    String title;

    @NotBlank(message = "The book author must be defined.")
    String author;

    @NotNull(message = "The book price must be defined.")
    @Positive(message = "The book price must be greater than zero.")
    Double price;

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Double getPrice() {
        return price;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle( String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPrice(@NotNull(message = "The book price must be defined.") @Positive(message = "The book price must be greater than zero.") Double price) {
        this.price = price;
    }
}
