package com.example.rebookbookservice.controller;

import com.example.rebookbookservice.common.CommonResult;
import com.example.rebookbookservice.common.ResponseService;
import com.example.rebookbookservice.model.BookReviewRequest;
import com.example.rebookbookservice.service.BookReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
        @RequestBody BookReviewRequest request,
        @RequestParam String userId
    ){
        bookReviewService.createBookReview(request, bookId, userId);
        return ResponseService.getSuccessResult();
    }
}
