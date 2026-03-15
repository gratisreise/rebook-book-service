package com.example.rebookbookservice.domain.book.model;

public record Item(
    String title,
    String link,
    String author,
    String image,
    String discount,
    String publisher,
    String pubdate,
    String isbn,
    String description
) {
}
