package com.polarbookshop.catalogservice.controllers;

import com.polarbookshop.catalogservice.domain.BookServiceImpl;
import com.polarbookshop.catalogservice.exceptions.BookNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;

// TODO 5. Finish the implementation of tests with WebMvc.
//  Review concepts and differences from SpringBootTest, such
//  as WebMvcTest will only start the necessary beans.
@WebMvcTest(BookController.class)
class BookControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookServiceImpl bookService;

    @Test
    void whenGetBookingNotExistingThenShouldReturn404() throws Exception {
        String isbn = "73737313940";
        given(bookService.viewBookDetails(isbn)).willThrow(BookNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/books/" + isbn))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void getBookByIsbn() {
    }

    @Test
    void post() {
    }

    @Test
    void delete() {
    }

    @Test
    void put() {
    }
}