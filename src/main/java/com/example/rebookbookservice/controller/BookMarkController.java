package com.example.rebookbookservice.controller;

import com.example.rebookbookservice.common.CommonResult;
import com.example.rebookbookservice.common.PageResponse;
import com.example.rebookbookservice.common.ResponseService;
import com.example.rebookbookservice.common.SingleResult;
import com.example.rebookbookservice.model.entity.Book;
import com.example.rebookbookservice.service.BookMarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookMarkController {
    private final BookMarkService bookMarkService;

    //@RequestHeader("X-User-Id") String userId
    //
    @PostMapping("/{bookId}/marks")
    public CommonResult markingToggle(@RequestHeader("X-User-Id") String userId, @PathVariable Long bookId) {
        bookMarkService.markingToggle(userId, bookId);
        return ResponseService.getSuccessResult();
    }

    //@RequestHeader("X-User-Id") String userId
    @GetMapping("/marks")
    public SingleResult<PageResponse<Book>> getMarkedBooks(@RequestHeader("X-User-Id") String userId, @PageableDefault Pageable pageable) {
        return ResponseService.getSingleResult(bookMarkService.getMarkedBooks(userId, pageable));
    }

}
