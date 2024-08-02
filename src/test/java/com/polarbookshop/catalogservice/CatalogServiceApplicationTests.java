package com.polarbookshop.catalogservice;

import com.polarbookshop.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


// TODO 4. Implement rest of integration tests
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class CatalogServiceApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void contextLoads() {
    }

    @Test
    void whenPostRequestToAddBookThenBookIsCreated() {
        var expectedBook = new Book("1234567890", "title", "author", 9.90);
        webTestClient
                .post()
                .uri("/books")
                .bodyValue(expectedBook)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class)
                .value(actualBook -> {
                    assertNotNull(actualBook);
                    assertEquals(expectedBook.isbn(), actualBook.isbn());
                    assertEquals(expectedBook.title(), actualBook.title());
                    assertEquals(expectedBook.author(), actualBook.author());
                    assertEquals(expectedBook.price(), actualBook.price());
                });
    }
}
