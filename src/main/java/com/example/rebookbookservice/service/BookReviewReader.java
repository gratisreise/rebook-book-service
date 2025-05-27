package com.example.rebookbookservice.service;

import com.example.rebookbookservice.exception.CMissingDataException;
import com.example.rebookbookservice.model.entity.BookReview;
import com.example.rebookbookservice.repository.BookReviewRepository;
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
