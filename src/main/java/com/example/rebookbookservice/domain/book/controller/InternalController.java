package com.example.rebookbookservice.domain.book.controller;

import com.example.rebookbookservice.domain.book.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/books")
public class InternalController {
  private final BookService bookService;

  @GetMapping("/recommendations/{userId}")
  public List<Long> recommendedBookIds(@PathVariable String userId) {
    return bookService.getRecommendedBookIds(userId);
  }

  @GetMapping("/alarms/books/{bookId}")
  public List<String> getUserIdsByBookId(@PathVariable Long bookId) {
    return bookService.getUserIdsByBookId(bookId);
  }
}
