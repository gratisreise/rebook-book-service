package com.example.rebookbookservice.domain.book.controller;

import com.example.rebookbookservice.domain.book.service.BookReader;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReaderController {

    private final BookReader bookReader;

    @GetMapping("/alarms/books/{bookId}")
    @Operation(summary = "유저Id목록조회", description = "내부 서비스 통신용 api")
    public List<String> getUserIdsByBookId(@PathVariable Long bookId){
        return bookReader.getUserIdsByBookId(bookId);
    }
}
