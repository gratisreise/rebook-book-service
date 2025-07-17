package com.example.rebookbookservice.service;

import com.example.rebookbookservice.exception.CMissingDataException;
import com.example.rebookbookservice.model.entity.Book;
import com.example.rebookbookservice.repository.BookRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BookReader {
    private final BookRepository bookRepository;


    public Book readBookById(Long bookId) {
        return bookRepository.findById(bookId)
            .orElseThrow(CMissingDataException::new);
    }

    public List<Book> readBookByCategoryIn(List<String> categories) {
        Pageable pageable = PageRequest.of(0, 10);
        log.info("Reading books by category: {}", categories);
        Page<Book> books =  bookRepository.findByCategoryIn(categories, pageable);
        List<Book> bookList = bookRepository.findByCategoryIn(categories);
        Book book = bookRepository.findByCategory("소설");
        log.info("book {}", book);
        log.info("books: {} ", bookList.toString());
        log.info("books count: {}", books.getContent());
        return bookList;
    }
    public boolean existsByIsbn(String isbn){
        return bookRepository.existsByIsbn(isbn);
    }
}
