package com.polarbookshop.catalogservice.demo;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.polarbookshop.catalogservice.domain.dtos.BookDTOs;
import com.polarbookshop.catalogservice.domain.mappers.BookMapper;
import com.polarbookshop.catalogservice.persistence.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.TypeReference;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
@Profile("testData")
public class BookDataLoader {

    private static Logger log = LoggerFactory.getLogger(BookDataLoader.class);
    private final ObjectMapper objectMapper;
    private final BookMapper bookMapper;
    private final BookRepository bookRepository;


    public BookDataLoader(ObjectMapper objectMapper, BookMapper bookMapper, BookRepository bookRepository) {
        this.objectMapper = objectMapper;
        this.bookMapper = bookMapper;
        this.bookRepository = bookRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadBookTestData() {
        bookRepository.deleteAll();
        if(bookRepository.count() == 0){
            String booksJson = "/data/books.json";
            log.info("Loading books into database from JSON: {}", booksJson);

            try(InputStream inputStream = TypeReference.class.getResourceAsStream(booksJson)){
                BookDTOs response = objectMapper.readValue(inputStream, BookDTOs.class);
                var books = bookMapper.bookDTOsToBooks(response.getBooks());
                bookRepository.saveAll(books);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
//        bookRepository.deleteAll();
//        var book1 = Book.of("1234567891", "Northern Lights",
//                "Lyra Silverstar", 9.90);
//        var book2 = Book.of("1234567892", "Polar Journey",
//                "Iorek Polarson", 12.90);
//        bookRepository.saveAll(List.of(book1, book2));
    }
}
