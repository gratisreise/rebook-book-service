package com.example.rebookbookservice.domain.book.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record BookReviewRequest(
    @NotBlank
    @Length(min = 5, max = 100)
    String content,

    int score
) {
}
