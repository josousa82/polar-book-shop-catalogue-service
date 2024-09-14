package com.polarbookshop.catalogservice.domain.dtos;

import java.util.List;

public class BookDTOs {
   private List<BookDTO> books;

    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }
}
