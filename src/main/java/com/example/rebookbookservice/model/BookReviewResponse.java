package com.example.rebookbookservice.model;

import com.example.rebookbookservice.model.entity.BookReview;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookReviewResponse {
    private Long bookId;
    private String author;
    private String content;
    private int score;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BookReviewResponse(BookReview bookReview, String author) {
        this.bookId = bookReview.getId();
        this.content = bookReview.getContent();
        this.score = bookReview.getScore();
        this.author = author;
        this.createdAt = bookReview.getCreatedAt();
        this.updatedAt = bookReview.getUpdatedAt();
    }
}
