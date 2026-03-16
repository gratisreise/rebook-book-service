package com.example.rebookbookservice.domain.book.service.reader;

import com.example.rebookbookservice.domain.book.model.entity.BookReview;
import com.example.rebookbookservice.domain.book.repository.BookReviewRepository;
import com.example.rebookbookservice.common.exception.BookException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookReviewReader {
    private final BookReviewRepository bookReviewRepository;

    public BookReview readReview(Long reviewId) {
        return bookReviewRepository.findById(reviewId)
            .orElseThrow(BookException::new);
    }
}
