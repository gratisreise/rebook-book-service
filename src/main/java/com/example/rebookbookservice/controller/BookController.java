package com.example.rebookbookservice.controller;

import com.example.rebookbookservice.common.CommonResult;
import com.example.rebookbookservice.common.PageResponse;
import com.example.rebookbookservice.common.ResponseService;
import com.example.rebookbookservice.common.SingleResult;
import com.example.rebookbookservice.model.BookRequest;
import com.example.rebookbookservice.model.entity.Book;
import com.example.rebookbookservice.model.naver.NaverBooksResponse;
import com.example.rebookbookservice.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        return ResponseService.getSingleResult(bookService.searchNaverBooks(keyword));
    }

    @PostMapping
    public CommonResult postBook(@Valid @RequestBody BookRequest request) {
        bookService.postBook(request);
        return ResponseService.getSuccessResult();
    }

    @GetMapping("/search")
    public SingleResult<PageResponse<Book>> search(
        @RequestParam String keyword,
        @PageableDefault(sort = "id", direction = Direction.ASC) Pageable pageable
    ) {
        return ResponseService.getSingleResult(bookService.searchBooks(keyword, pageable));
    }

    @GetMapping("/{bookId}")
    public SingleResult<Book> getBook(@PathVariable Long bookId) {
        return ResponseService.getSingleResult(bookService.getBook(bookId));
    }




}
