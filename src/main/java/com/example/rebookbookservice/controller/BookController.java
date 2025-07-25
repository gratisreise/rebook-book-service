package com.example.rebookbookservice.controller;

import com.example.rebookbookservice.common.CommonResult;
import com.example.rebookbookservice.common.PageResponse;
import com.example.rebookbookservice.common.ResponseService;
import com.example.rebookbookservice.common.SingleResult;
import com.example.rebookbookservice.model.BookRequest;
import com.example.rebookbookservice.model.BookResponse;
import com.example.rebookbookservice.model.naver.NaverBooksResponse;
import com.example.rebookbookservice.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
@Tag(name="도서")
public class BookController {
    private final BookService bookService;

    @GetMapping("/external/search")
    @Operation(summary = "네이버 Api 도서 검색")
    public SingleResult<NaverBooksResponse> externalSearch(@RequestParam String keyword) {
        return ResponseService.getSingleResult(bookService.searchNaverBooks(keyword));
    }

    @PostMapping
    @Operation(summary = "도서등록")
    public CommonResult postBook(@Valid @RequestBody BookRequest request) {
        bookService.postBook(request);
        return ResponseService.getSuccessResult();
    }

    @GetMapping
    @Operation(summary = "도서조회")
    public SingleResult<PageResponse<BookResponse>> getBooks(
        @RequestHeader("X-User-Id") String userId,
        @PageableDefault Pageable pageable){
        return ResponseService.getSingleResult(bookService.getBooks(userId, pageable));
    }

    @GetMapping("/search")
    @Operation(summary = "도서검색")
    public SingleResult<PageResponse<BookResponse>> search(
        @RequestParam String keyword, @PageableDefault Pageable pageable
    ) {
        return ResponseService.getSingleResult(bookService.searchBooks(keyword, pageable));
    }

    @GetMapping("/{bookId}")
    public SingleResult<BookResponse> getBook(
        @RequestHeader("X-User-Id") String userId, @PathVariable Long bookId) {
        return ResponseService.getSingleResult(bookService.getBook(userId, bookId));
    }


    @GetMapping("/recommendations")
    @Operation(summary = "추천도서조회")
    public List<Long> recommendations(@RequestHeader("X-User-Id") String userId) {
        return bookService.getRecommendedBookIds(userId);
    }

    //거래서비스 호출용
    @GetMapping("/recommendations/{userId}")
    public List<Long> recommendedBookIds(@PathVariable String userId) {
        return bookService.getRecommendedBookIds(userId);
    }
}
