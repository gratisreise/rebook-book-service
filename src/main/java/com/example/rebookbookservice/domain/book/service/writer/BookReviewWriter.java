package com.example.rebookbookservice.domain.book.service.writer;

import com.example.rebookbookservice.common.exception.BookException;
import com.example.rebookbookservice.domain.book.model.dto.request.BookReviewRequest;
import com.example.rebookbookservice.domain.book.model.entity.Book;
import com.example.rebookbookservice.domain.book.model.entity.BookReview;
import com.example.rebookbookservice.domain.book.repository.BookReviewRepository;
import com.example.rebookbookservice.domain.book.service.reader.BookReader;
import com.example.rebookbookservice.domain.book.service.reader.BookReviewReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BookReviewWriter {
  private final BookReviewRepository bookReviewRepository;
  private final BookReviewReader bookReviewReader;
  private final BookReader bookReader;
  private final BookWriter bookWriter;

  public void createBookReview(BookReviewRequest request, Long bookId, String userId) {
    if (bookReviewRepository.existsByBookIdAndUserId(bookId, userId)) {
      throw new BookException();
    }

    Book book = bookReader.readBookById(bookId);
    BookReview bookReview = request.toEntity(book, userId);
    bookReviewRepository.save(bookReview);

    starRatingUpdate(bookId, book);
  }

  public void updateBookReview(BookReviewRequest request, Long reviewId, Long bookId) {
    BookReview review = bookReviewReader.readReview(reviewId);
    review.update(request);
    Book book = bookReader.readBookById(bookId);
    starRatingUpdate(bookId, book);
  }

  public void deleteBookReview(String userId, Long reviewId, Long bookId) {
    BookReview review = bookReviewReader.readReview(reviewId);
    if (!userId.equals(review.getUserId())) {
      log.error("Unauthorized to delete book review");
      throw new BookException();
    }
    bookReviewRepository.delete(review);
    Book book = bookReader.readBookById(bookId);
    starRatingUpdate(bookId, book);
  }

  private void starRatingUpdate(Long bookId, Book book) {
    float ratingAvg = bookReviewRepository.findAverageScoreByBookId(bookId);
    bookWriter.updateBookRating(book, ratingAvg);
  }
}
