package com.example.rebookbookservice.controller;

import com.example.rebookbookservice.service.BookReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookReviewController {
    private final BookReviewService bookReviewService;

}
