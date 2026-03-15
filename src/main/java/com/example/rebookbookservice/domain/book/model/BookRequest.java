package com.example.rebookbookservice.domain.book.model;

public record BookRequest(
    String title,
    String author,
    String publisher,
    String isbn,
    String description,
    Integer price,
    String publishedDate,
    String cover
) {
}
