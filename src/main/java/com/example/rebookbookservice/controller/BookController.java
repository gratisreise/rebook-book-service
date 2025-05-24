package com.example.rebookbookservice.controller;

import com.example.rebookbookservice.common.ResponseService;
import com.example.rebookbookservice.common.SingleResult;
import com.example.rebookbookservice.model.naver.NaverBooksResponse;
import com.example.rebookbookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    @GetMapping("/external/search")
    public SingleResult<NaverBooksResponse> externalSearch(@RequestParam String keyword) {
        return ResponseService.getSingleResult(bookService.getNaverBooks(keyword));
    }

}
