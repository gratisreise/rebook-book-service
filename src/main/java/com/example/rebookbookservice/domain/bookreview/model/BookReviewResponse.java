package com.example.rebookbookservice.domain.bookreview.model;

import com.example.rebookbookservice.domain.bookreview.model.entity.BookReview;
import java.time.LocalDateTime;

public record BookReviewResponse(
    Long reviewId,
    Long bookId,
    String author,
    String content,
    int score,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public BookReviewResponse(BookReview bookReview, String author) {
        this(
            bookReview.getId(),
            bookReview.getId(),
            author,
            bookReview.getContent(),
            bookReview.getScore(),
            bookReview.getCreatedAt(),
            bookReview.getUpdatedAt()
        );
    }
}
