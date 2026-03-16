package com.example.rebookbookservice.domain.book.model.dto.response;

import com.example.rebookbookservice.domain.book.model.entity.Book;
import java.time.LocalDate;
import lombok.Builder;

@Builder(toBuilder = true)
public record BookResponse(
    Long bookId,
    String title,
    String author,
    String publisher,
    String isbn,
    String description,
    LocalDate publishedDate,
    String cover,
    String category,
    float rating,
    Integer price,
    boolean isMarked) {
  public static BookResponse from(Book book) {
    return BookResponse.builder()
        .bookId(book.getId())
        .title(book.getTitle())
        .author(book.getAuthor())
        .publisher(book.getPublisher())
        .isbn(book.getIsbn())
        .description(book.getDescription())
        .publishedDate(book.getPublishedDate())
        .cover(book.getCover())
        .category(book.getCategory().name())
        .rating(book.getRating())
        .price(book.getPrice())
        .isMarked(false)
        .build();
  }

  public BookResponse withMarked(boolean marked) {
    return this.toBuilder().isMarked(marked).build();
  }
}
