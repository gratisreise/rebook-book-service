package com.example.rebookbookservice.domain.book.model.dto.request;

import com.example.rebookbookservice.common.enums.Category;
import com.example.rebookbookservice.domain.book.model.entity.Book;
import java.time.LocalDate;

public record BookRequest(
    String title,
    String author,
    String publisher,
    String isbn,
    String description,
    Integer price,
    String publishedDate,
    String cover) {
  public Book toEntity(Category category, LocalDate publishedDate) {
    return Book.builder()
        .title(this.title)
        .author(this.author)
        .publisher(this.publisher)
        .publishedDate(publishedDate)
        .isbn(this.isbn)
        .description(this.description)
        .cover(this.cover)
        .category(category)
        .price(this.price)
        .build();
  }
}
