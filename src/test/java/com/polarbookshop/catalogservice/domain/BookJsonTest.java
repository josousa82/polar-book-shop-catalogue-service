package com.polarbookshop.catalogservice.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
class BookJsonTest {

    @Autowired
    private JacksonTester<Book> json;

    private final ObjectMapper objectMapper = new ObjectMapper();

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
        var expectedBook =  Book.of("1234567890", "Title", "Author", 9.90);

        var jsonContent = json.parse(content);
        assertThat(jsonContent).usingRecursiveComparison().isEqualTo(expectedBook);
    }
}