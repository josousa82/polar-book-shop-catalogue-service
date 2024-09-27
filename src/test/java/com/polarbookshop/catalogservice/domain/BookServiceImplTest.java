package com.polarbookshop.catalogservice.domain;

import com.polarbookshop.catalogservice.domain.dtos.BookDTO;
import com.polarbookshop.catalogservice.domain.mappers.BookMapper;
import com.polarbookshop.catalogservice.domain.model.Book;
import com.polarbookshop.catalogservice.persistence.BookRepository;
import com.polarbookshop.catalogservice.testUtils.TestDataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenGetBooksIsCalledThenAListOfBookDTOSIsReturned() {
        var books = TestDataUtils.getTestBooksList();
        var bookDTOs = TestDataUtils.getTestBookDTOSList();

        when(bookRepository.findAll()).thenReturn(books);
        when(bookMapper.booksToBooksDTOs(books)).thenReturn(bookDTOs);

        List<BookDTO> result = bookService.viewBookList();

        assertEquals(bookDTOs, result);
    }

    @Test
    void whenGetBooksIsCalledAndThereIsNoBooksThenAnEmptyListIsReturned() {
        when(bookRepository.findAll()).thenReturn(Collections.emptyList());
        when(bookMapper.booksToBooksDTOs(anyList())).thenReturn(Collections.emptyList());

        List<BookDTO> result = bookService.viewBookList();

        assertTrue(result.isEmpty());
    }

    @Test
    void viewBookDetails() {
    }

    @Test
    void addBookToCatalog() {
    }

    @Test
    void removeBookFromCatalog() {
    }

    @Test
    void editBookDetails() {
    }
}