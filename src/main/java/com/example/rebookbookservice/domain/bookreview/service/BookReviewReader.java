package com.example.rebookbookservice.domain.bookreview.service;

import com.example.rebookbookservice.domain.bookreview.model.entity.BookReview;
import com.example.rebookbookservice.domain.bookreview.repository.BookReviewRepository;
import com.example.rebookbookservice.exception.CMissingDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookReviewReader {
    private final BookReviewRepository bookReviewRepository;

    public BookReview readReview(Long reviewId) {
        return bookReviewRepository.findById(reviewId)
            .orElseThrow(CMissingDataException::new);
    }
}
