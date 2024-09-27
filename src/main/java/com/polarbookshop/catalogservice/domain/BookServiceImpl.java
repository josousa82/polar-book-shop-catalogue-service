package com.polarbookshop.catalogservice.domain;


import com.polarbookshop.catalogservice.domain.dtos.BookDTO;
import com.polarbookshop.catalogservice.domain.mappers.BookMapper;
import com.polarbookshop.catalogservice.domain.model.Book;
import com.polarbookshop.catalogservice.exceptions.BookAlreadyExistsException;
import com.polarbookshop.catalogservice.exceptions.BookNotFoundException;
import com.polarbookshop.catalogservice.persistence.BookRepository;
import com.polarbookshop.catalogservice.validators.annotations.ValidIsbn;
import org.springframework.stereotype.Service;

import java.util.List;


// TODO 2. Implement unit tests for service
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper){
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public List<BookDTO> viewBookList(){
        return bookMapper.booksToBooksDTOs((List<Book>) bookRepository.findAll());
    }

    public BookDTO viewBookDetails(@ValidIsbn String isbn){
        var book =  bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
       return bookMapper.bookToBookDTO(book);
    }

    public BookDTO addBookToCatalog(BookDTO bookDTO){
        var book = bookMapper.bookDTOToBook(bookDTO);
        if (bookRepository.existsByIsbn(book.isbn())){
            throw new BookAlreadyExistsException(book.isbn());
        }
        var savedBook = bookRepository.save(book);
        return bookMapper.bookToBookDTO(savedBook);
    }

    public void removeBookFromCatalog(@ValidIsbn String isbn){
        bookRepository.deleteByIsbn(isbn);
    }

    public BookDTO editBookDetails(@ValidIsbn String isbn, BookDTO bookDTO){
        var book = bookMapper.bookDTOToBook(bookDTO);
        var updatedBook = bookRepository.findByIsbn(isbn)
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
                .orElseGet(() -> bookMapper.bookDTOToBook(addBookToCatalog(bookDTO)));
        return bookMapper.bookToBookDTO(updatedBook);
    }

}
