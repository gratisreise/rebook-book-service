package com.example.rebookbookservice.service;

import com.example.rebookbookservice.exception.CMissingDataException;
import com.example.rebookbookservice.feigns.UserClient;
import com.example.rebookbookservice.model.BookReviewRequest;
import com.example.rebookbookservice.model.entity.Book;
import com.example.rebookbookservice.model.entity.BookReview;
import com.example.rebookbookservice.repository.BookReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookReviewService {
    private final BookReviewRepository bookReviewRepository;
    private final BookReviewReader bookReviewReader;
    private final UserClient userClient;

    @Transactional
    public void createBookReview(BookReviewRequest request, Long bookId, String userId) {
        Book book = new Book(bookId);
        BookReview bookReview = new BookReview(request, book, userId);
        bookReviewRepository.save(bookReview);
    }

    @Transactional
    public void updateBookReview(BookReviewRequest request, Long reviewId) {
        BookReview review = bookReviewReader.readReview(reviewId);
        review.update(request);
    }
}
