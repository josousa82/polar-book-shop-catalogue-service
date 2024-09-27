package com.polarbookshop.catalogservice.controllers;

import com.polarbookshop.catalogservice.domain.BookService;
import com.polarbookshop.catalogservice.domain.dtos.BookDTO;
import com.polarbookshop.catalogservice.domain.BookServiceImpl;
import com.polarbookshop.catalogservice.validators.annotations.ValidIsbn;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("books")
public class BookController {
    private final BookService bookService;

    public BookController(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDTO> get() {
        return bookService.viewBookList();
    }

    @GetMapping("{isbn}")
    public BookDTO getBookByIsbn(@ValidIsbn @PathVariable String isbn) {
        return bookService.viewBookDetails(isbn);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO post(@Valid @RequestBody BookDTO bookDTO) {

        return bookService.addBookToCatalog(bookDTO);
    }

    @DeleteMapping("{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String isbn) {
        bookService.removeBookFromCatalog(isbn);
    }

    @PutMapping("{isbn}")
    public BookDTO put(@PathVariable @ValidIsbn String isbn, @Valid @RequestBody BookDTO bookDTO) {
        return bookService.editBookDetails(isbn, bookDTO);
    }
}
