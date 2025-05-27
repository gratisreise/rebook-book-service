package com.example.rebookbookservice.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class BookReviewRequest {
    @NotBlank
    @Length(min = 5, max = 100)
    private String content;

    private int score;
}
