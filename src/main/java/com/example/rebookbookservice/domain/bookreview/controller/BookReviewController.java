package com.example.rebookbookservice.domain.bookreview.controller;

import com.example.rebookbookservice.common.CommonResult;
import com.example.rebookbookservice.common.PageResponse;
import com.example.rebookbookservice.common.ResponseService;
import com.example.rebookbookservice.common.SingleResult;
import com.example.rebookbookservice.domain.bookreview.model.BookReviewRequest;
import com.example.rebookbookservice.domain.bookreview.model.BookReviewResponse;
import com.example.rebookbookservice.domain.bookreview.service.BookReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Tag(name="도서리뷰")
public class BookReviewController {
    private final BookReviewService bookReviewService;

    @PostMapping("/{bookId}/reviews")
    @Operation(summary = "도서리뷰등록")
    public CommonResult createReview(
        @PathVariable Long bookId,
        @Valid @RequestBody BookReviewRequest request,
        @RequestHeader("X-User-Id") String userId
    ){
        bookReviewService.createBookReview(request, bookId, userId);
        return ResponseService.getSuccessResult();
    }

    @PutMapping("/{bookId}/reviews/{reviewId}")
    @Operation(summary = "도서리뷰수정")
    public CommonResult updateReview(
        @PathVariable Long reviewId,
        @PathVariable Long bookId,
        @Valid @RequestBody BookReviewRequest request
    ){
      bookReviewService.updateBookReview(request, reviewId, bookId);
      return ResponseService.getSuccessResult();
    }

    @DeleteMapping("/{bookId}/reviews/{reviewId}")
    @Operation(summary = "도서리뷰삭제")
    public CommonResult deleteReview(
        @RequestHeader("X-User-Id") String userId,
        @PathVariable Long reviewId,
        @PathVariable Long bookId
    ){
        bookReviewService.deleteBookReview(userId, reviewId, bookId);
        return ResponseService.getSuccessResult();
    }

    @GetMapping("/{bookId}/reviews")
    @Operation(summary = "도서리뷰조회")
    public SingleResult<PageResponse<BookReviewResponse>> getReviews(@PathVariable Long bookId, @PageableDefault Pageable pageable){
        return ResponseService.getSingleResult(bookReviewService.getReviews(bookId, pageable));
    }
}
