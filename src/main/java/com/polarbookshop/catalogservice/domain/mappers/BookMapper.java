package com.polarbookshop.catalogservice.domain.mappers;

import com.polarbookshop.catalogservice.domain.dtos.BookDTO;
import com.polarbookshop.catalogservice.domain.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDTO bookToBookDTO(Book book);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isbn", source = "isbn")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "lastModifiedDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "version", constant = "0")
    Book bookDTOToBook(BookDTO bookDTO);

    List<BookDTO> booksToBooksDTOs(List<Book> books);
    List<Book> bookDTOsToBooks(List<BookDTO> bookDTOs);

    //  Custom Mapping Logic: Use default methods for fields that require transformation logic beyond simple property mapping.
    //	Referencing Custom Methods: MapStruct automatically recognizes and uses these methods during the mapping process.

    // Custom mapping for price
    //    default String mapPrice(BigDecimal price) {
    //        return "$" + price.setScale(2, RoundingMode.HALF_UP).toString();
    //    }
    //
    //    default BigDecimal mapCost(String cost) {
    //        return new BigDecimal(cost.replace("$", ""));
    //    }
}
