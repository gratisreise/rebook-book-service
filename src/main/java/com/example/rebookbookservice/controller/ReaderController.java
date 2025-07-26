package com.example.rebookbookservice.controller;

import com.example.rebookbookservice.service.BookReader;
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
    public List<String> getUserIdsByBookId(@PathVariable Long bookId){
        return bookReader.getUserIdsByBookId(bookId);
    }


}
