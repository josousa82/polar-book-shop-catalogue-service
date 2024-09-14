package com.polarbookshop.catalogservice;

import com.polarbookshop.catalogservice.config.DataConfig;
import com.polarbookshop.catalogservice.domain.model.Book;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


// TODO 4. Implement rest of integration tests to test all the endpoints
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("integration")
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CatalogServiceApplicationTests {

    @Container
    static PostgreSQLContainer<?> postgresDB = new PostgreSQLContainer<>("postgres:14.4")
            .withDatabaseName("polardb")
            .withUsername("postgres")
            .withPassword("postgres");

    @Autowired
    private WebTestClient webTestClient;

    static {
        postgresDB.start();
        System.setProperty("DB_URL", postgresDB.getJdbcUrl());
        System.setProperty("DB_USERNAME", postgresDB.getUsername());
        System.setProperty("DB_PASSWORD", postgresDB.getPassword());
    }

    @Ignore
    void whenGetRequestWithIdThenBookReturned() {
        var bookIsbn = "1231231230";
        var bookToCreate = Book.of(bookIsbn, "Title", "Author", 9.90);
        Book expectedBook = webTestClient
                .post()
                .uri("/books")
                .bodyValue(bookToCreate)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).value(book -> assertThat(book).isNotNull())
                .returnResult().getResponseBody();

        webTestClient
                .get()
                .uri("/books/" + bookIsbn)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                    assertThat(actualBook.isbn()).isEqualTo(expectedBook.isbn());
                });
    }

    @Ignore
    void whenPostRequestToAddBookThenBookIsCreated() {
        var expectedBook =  Book.of("1234567891", "title", "author", 9.90);
        webTestClient
                .post()
                .uri("/books")
                .bodyValue(expectedBook)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class)
                .value(actualBook -> {
                    assertThat(actualBook).isNotNull();
//                    assertEquals(expectedBook.isbn(), actualBook.isbn());
//                    assertEquals(expectedBook.title(), actualBook.title());
//                    assertEquals(expectedBook.author(), actualBook.author());
//                    assertEquals(expectedBook.price(), actualBook.price());
                });
    }

    @Ignore
    void whenPostRequestThenBookCreated() {
//        String isbn = "1231231231";
        String isbn = "1231212345";
        var expectedBook = Book.of(isbn, "Title", "Author", 9.90);

        webTestClient
                .post()
                .uri("/books")
                .bodyValue(expectedBook)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                    assertThat(actualBook.isbn()).isEqualTo(expectedBook.isbn());
                });
    }
}
