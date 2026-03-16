package com.example.rebookbookservice.domain.book.model.dto.response;

import com.example.rebookbookservice.domain.book.model.entity.Book;
import java.time.LocalDate;

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
    boolean isMarked
) {
    public BookResponse(Book book) {
        this(
            book.getId(),
            book.getTitle(),
            book.getAuthor(),
            book.getPublisher(),
            book.getIsbn(),
            book.getDescription(),
            book.getPublishedDate(),
            book.getCover(),
            book.getCategory().name(),
            book.getRating(),
            book.getPrice(),
            false
        );
    }

    public BookResponse withMarked(boolean marked) {
        return new BookResponse(
            this.bookId,
            this.title,
            this.author,
            this.publisher,
            this.isbn,
            this.description,
            this.publishedDate,
            this.cover,
            this.category,
            this.rating,
            this.price,
            marked
        );
    }
}
