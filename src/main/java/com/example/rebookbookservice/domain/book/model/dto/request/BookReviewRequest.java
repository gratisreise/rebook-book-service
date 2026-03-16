package com.example.rebookbookservice.domain.book.model.dto.request;

import com.example.rebookbookservice.domain.book.model.entity.Book;
import com.example.rebookbookservice.domain.book.model.entity.BookReview;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record BookReviewRequest(@NotBlank @Length(min = 5, max = 100) String content, int score) {
  public BookReview toEntity(Book book, String userId) {
    return BookReview.builder().book(book).userId(userId).content(this.content).score(this.score).build();
  }
}
