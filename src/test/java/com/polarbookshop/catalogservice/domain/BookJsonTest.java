package com.polarbookshop.catalogservice.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polarbookshop.catalogservice.domain.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@JsonTest
class BookJsonTest {

    @Autowired
    private JacksonTester<Book> json;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JdbcAggregateTemplate jdbcAggregateTemplate;

    @Test
    void testSerialize() throws IOException {
        var book = Book.of("1234567890", "title", "author", 9.90);
        JsonContent<Book> jsonContent = json.write(book);
        JsonNode jsonNode = objectMapper.readTree(jsonContent.getJson());
        assertEquals("1234567890", jsonNode.get("isbn").asText());
        assertEquals("title", jsonNode.get("title").asText());
        assertEquals("author", jsonNode.get("author").asText());
        assertEquals(9.90, jsonNode.get("price").asDouble());
    }

    @Test
    void testDeserialize() throws IOException {
        var content = """
                        {
                        "isbn": "1234567890",
                        "title": "Title",
                        "author": "Author",
                        "price": 9.90
                        }
                    """;
        Instant mockInstant = Instant.parse("2024-08-24T10:00:00Z");
        var expectedBook =  new Book(1L,"1234567890", "Title", "Author", 9.90, mockInstant, mockInstant, 0);
        when(jdbcAggregateTemplate.insert(expectedBook)).thenReturn(expectedBook);

        var bookJson = json.parse(content);

        assertThat(bookJson).usingRecursiveComparison()
                .ignoringFields("id","createdDate","lastModifiedDate", "version")
                .isEqualTo(expectedBook);
//        assertEquals(bookJson);
    }
}