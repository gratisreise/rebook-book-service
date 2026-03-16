package com.example.rebookbookservice.domain.book.model.dto.response;

import com.example.rebookbookservice.domain.book.model.entity.BookReview;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record BookReviewResponse(
    Long reviewId,
    Long bookId,
    String author,
    String content,
    int score,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {
  public static BookReviewResponse from(BookReview bookReview, String author) {
    return BookReviewResponse.builder()
        .reviewId(bookReview.getId())
        .bookId(bookReview.getBook().getId())
        .author(author)
        .content(bookReview.getContent())
        .score(bookReview.getScore())
        .createdAt(bookReview.getCreatedAt())
        .updatedAt(bookReview.getUpdatedAt())
        .build();
  }
}
