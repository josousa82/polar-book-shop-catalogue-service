package com.polarbookshop.catalogservice.testUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polarbookshop.catalogservice.domain.dtos.BookDTO;
import com.polarbookshop.catalogservice.domain.dtos.BookDTOs;
import com.polarbookshop.catalogservice.domain.mappers.BookMapper;
import com.polarbookshop.catalogservice.domain.model.Book;
import org.mapstruct.factory.Mappers;
import org.springframework.asm.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TestDataUtils {

    private static final ObjectMapper objectMapper;
    private static final BookMapper bookMapper;

    static {
        objectMapper = new ObjectMapper();
        bookMapper = Mappers.getMapper(BookMapper.class);
    }


    public static List<Book> getTestBooksList() {
        return bookMapper.bookDTOsToBooks(getTestBookDTOSList());
    }

    public static List<BookDTO> getTestBookDTOSList() {
        String booksJson = "/data/books.json";

        try(InputStream inputStream = TypeReference.class.getResourceAsStream(booksJson)){
            return objectMapper.readValue(inputStream, BookDTOs.class).getBooks();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
