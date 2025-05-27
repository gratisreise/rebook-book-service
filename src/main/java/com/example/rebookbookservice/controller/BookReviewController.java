package com.example.rebookbookservice.controller;

import com.example.rebookbookservice.common.CommonResult;
import com.example.rebookbookservice.common.PageResponse;
import com.example.rebookbookservice.common.ResponseService;
import com.example.rebookbookservice.common.SingleResult;
import com.example.rebookbookservice.model.BookReviewRequest;
import com.example.rebookbookservice.model.BookReviewResponse;
import com.example.rebookbookservice.model.entity.BookReview;
import com.example.rebookbookservice.service.BookReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookReviewController {
    private final BookReviewService bookReviewService;

    @PostMapping("/{bookId}/reviews")
    public CommonResult createReview(
        @PathVariable Long bookId,
        @Valid @RequestBody BookReviewRequest request,
        @RequestParam String userId
    ){
        bookReviewService.createBookReview(request, bookId, userId);
        return ResponseService.getSuccessResult();
    }

    @PutMapping("/reviews/{reviewId}")
    public CommonResult updateReview(
        @PathVariable Long reviewId,
        @Valid @RequestBody BookReviewRequest request
    ){
      bookReviewService.updateBookReview(request, reviewId);
      return ResponseService.getSuccessResult();
    }

    // @RequestHeader("X-User-Id") String userId,
    @DeleteMapping("/reviews/{reviewId}")
    public CommonResult deleteReview(
        @RequestParam String userId,
        @PathVariable Long reviewId
    ){
        bookReviewService.deleteBookReview(userId, reviewId);
        return ResponseService.getSuccessResult();
    }

    @GetMapping("/{bookId}/reviews")
    public SingleResult<PageResponse<BookReviewResponse>> getReviews(@PathVariable Long bookId, @PageableDefault Pageable pageable){
        return ResponseService.getSingleResult(bookReviewService.getReviews(bookId, pageable));
    }
}
