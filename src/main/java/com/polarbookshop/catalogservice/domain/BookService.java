package com.polarbookshop.catalogservice.domain;

import com.polarbookshop.catalogservice.domain.dtos.BookDTO;
import com.polarbookshop.catalogservice.validators.annotations.ValidIsbn;

import java.util.List;

public interface BookService {
    List<BookDTO> viewBookList();
    BookDTO viewBookDetails(@ValidIsbn String isbn);
    BookDTO addBookToCatalog(BookDTO bookDTO);
    void removeBookFromCatalog(@ValidIsbn String isbn);
    BookDTO editBookDetails(@ValidIsbn String isbn, BookDTO bookDTO);
}
