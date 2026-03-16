package com.example.rebookbookservice.domain.book.controller;

import com.example.rebookbookservice.domain.book.model.dto.request.BookRequest;
import com.example.rebookbookservice.domain.book.model.dto.request.BookReviewRequest;
import com.example.rebookbookservice.domain.book.model.dto.response.BookResponse;
import com.example.rebookbookservice.domain.book.model.dto.response.BookReviewResponse;
import com.example.rebookbookservice.domain.book.model.entity.Book;
import com.example.rebookbookservice.domain.book.service.BookService;
import com.example.rebookbookservice.external.naverbooks.NaverBooksResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.rebook.common.auth.PassportProto.Passport;
import com.rebook.common.auth.PassportUser;
import com.rebook.common.core.response.PageResponse;
import com.rebook.common.core.response.SuccessResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {
  private final BookService bookService;

  // === Book APIs ===

  @GetMapping("/test")
  public String test(@PassportUser Passport passport) {
    return passport.toString();
  }

  @GetMapping("/external/search")
  public ResponseEntity<SuccessResponse<NaverBooksResponse>> externalSearch(
      @RequestParam String keyword) {
    return SuccessResponse.toOk(bookService.searchNaverBooks(keyword));
  }

  @PostMapping
  public ResponseEntity<SuccessResponse<Void>> postBook(@Valid @RequestBody BookRequest request)
      throws JsonProcessingException {
    bookService.postBook(request);
    return SuccessResponse.toNoContent();
  }

  @GetMapping
  public ResponseEntity<SuccessResponse<PageResponse<BookResponse>>> getBooks(
      @PassportUser String userId, @PageableDefault Pageable pageable) {
    return SuccessResponse.toOk(bookService.getBooks(userId, pageable));
  }

  @GetMapping("/search")
  public ResponseEntity<SuccessResponse<PageResponse<BookResponse>>> search(
      @RequestParam String keyword, @PageableDefault Pageable pageable) {
    return SuccessResponse.toOk(bookService.searchBooks(keyword, pageable));
  }

  @GetMapping("/{bookId}")
  public ResponseEntity<SuccessResponse<BookResponse>> getBook(
      @PassportUser String userId, @PathVariable Long bookId) {
    return SuccessResponse.toOk(bookService.getBook(userId, bookId));
  }

  @GetMapping("/recommendations")
  public ResponseEntity<SuccessResponse<List<BookResponse>>> recommendations(
      @PassportUser String userId) {
    return SuccessResponse.toOk(bookService.getRecommendedBooks(userId));
  }

  @GetMapping("/recommendations/{userId}")
  public List<Long> recommendedBookIds(@PathVariable String userId) {
    return bookService.getRecommendedBookIds(userId);
  }

  // === BookMark APIs ===

  @PostMapping("/{bookId}/marks")
  public ResponseEntity<SuccessResponse<Void>> markingToggle(
      @PassportUser String userId, @PathVariable Long bookId) {
    bookService.markingToggle(userId, bookId);
    return SuccessResponse.toNoContent();
  }

  @GetMapping("/marks")
  public ResponseEntity<SuccessResponse<PageResponse<Book>>> getMarkedBooks(
      @PassportUser String userId, @PageableDefault Pageable pageable) {
    return SuccessResponse.toOk(bookService.getMarkedBooks(userId, pageable));
  }

  // === BookReview APIs ===

  @PostMapping("/{bookId}/reviews")
  public ResponseEntity<SuccessResponse<Void>> createReview(
      @PathVariable Long bookId,
      @Valid @RequestBody BookReviewRequest request,
      @PassportUser String userId) {
    bookService.createBookReview(request, bookId, userId);
    return SuccessResponse.toNoContent();
  }

  @PutMapping("/{bookId}/reviews/{reviewId}")
  public ResponseEntity<SuccessResponse<Void>> updateReview(
      @PathVariable Long reviewId,
      @PathVariable Long bookId,
      @Valid @RequestBody BookReviewRequest request) {
    bookService.updateBookReview(request, reviewId, bookId);
    return SuccessResponse.toNoContent();
  }

  @DeleteMapping("/{bookId}/reviews/{reviewId}")
  public ResponseEntity<SuccessResponse<Void>> deleteReview(
      @PassportUser String userId, @PathVariable Long reviewId, @PathVariable Long bookId) {
    bookService.deleteBookReview(userId, reviewId, bookId);
    return SuccessResponse.toNoContent();
  }

  @GetMapping("/{bookId}/reviews")
  public ResponseEntity<SuccessResponse<PageResponse<BookReviewResponse>>> getReviews(
      @PathVariable Long bookId, @PageableDefault Pageable pageable) {
    return SuccessResponse.toOk(bookService.getReviews(bookId, pageable));
  }

  // === Alarm APIs ===

  @GetMapping("/alarms/books/{bookId}")
  public List<String> getUserIdsByBookId(@PathVariable Long bookId) {
    return bookService.getUserIdsByBookId(bookId);
  }
}
