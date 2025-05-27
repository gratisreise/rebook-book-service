package com.example.rebookbookservice.service;

import com.example.rebookbookservice.exception.CMissingDataException;
import com.example.rebookbookservice.model.entity.Book;
import com.example.rebookbookservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookReader {
    private final BookRepository bookRepository;

    public Book readBookById(Long bookId) {
        return bookRepository.findById(bookId)
            .orElseThrow(CMissingDataException::new);
    }
}
