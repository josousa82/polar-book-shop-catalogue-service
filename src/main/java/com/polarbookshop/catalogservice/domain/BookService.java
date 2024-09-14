package com.polarbookshop.catalogservice.domain;


import com.polarbookshop.catalogservice.domain.dtos.BookDTO;
import com.polarbookshop.catalogservice.domain.mappers.BookMapper;
import com.polarbookshop.catalogservice.domain.model.Book;
import com.polarbookshop.catalogservice.exceptions.BookAlreadyExistsException;
import com.polarbookshop.catalogservice.exceptions.BookNotFoundException;
import com.polarbookshop.catalogservice.persistence.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;


// TODO 2. Implement unit tests for service
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper){
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public List<BookDTO> viewBookList(){
        return bookMapper.booksToBooksDTOs((List<Book>) bookRepository.findAll());
    }

    public Book viewBookDetails(String isbn){
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
    }

    public Book addBookToCatalog(Book book){
        if (bookRepository.existsByIsbn(book.isbn())){
            throw new BookAlreadyExistsException(book.isbn());
        }
        return bookRepository.save(book);
    }

    public void removeBookFromCatalog(String isbn){
        bookRepository.deleteByIsbn(isbn);
    }

    public Book editBookDetails(String isbn, Book book){
        return bookRepository.findByIsbn(isbn)
                .map(existingBook -> {
                    var bookToUpdate = new Book(
                            existingBook.id(),
                            existingBook.isbn(),
                            book.title(),
                            book.author(),
                            book.price(),
                            existingBook.createdDate(),
                            existingBook.lastModifiedDate(),
                            existingBook.version());
                    return bookRepository.save(bookToUpdate);
                } )
                .orElseGet(() -> addBookToCatalog(book));
    }

}
