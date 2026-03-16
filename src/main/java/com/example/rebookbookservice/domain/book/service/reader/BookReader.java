package com.example.rebookbookservice.domain.book.service.reader;

import com.example.rebookbookservice.domain.book.model.entity.Book;
import com.example.rebookbookservice.domain.book.repository.BookRepository;
import com.example.rebookbookservice.domain.book.repository.BookMarkRepository;
import com.example.rebookbookservice.common.exception.BookException;
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
    private final BookMarkRepository bookMarkRepository;

    public Book readBookById(Long bookId) {
        return bookRepository.findById(bookId)
            .orElseThrow(BookException::new);
    }

    public List<Book> readBookByCategoryIn(List<String> categories) {
        Pageable pageable = PageRequest.of(0, 10);
        log.info("Reading books by category: {}", categories);
        Page<Book> books = bookRepository.findByCategoryIn(categories, pageable);
        List<Book> bookList = bookRepository.findByCategoryIn(categories);
        log.info("books: {} ", bookList.toString());
        log.info("books count: {}", books.getContent());
        return bookList;
    }

    public boolean existsByIsbn(String isbn){
        return bookRepository.existsByIsbn(isbn);
    }

    public List<String> getUserIdsByBookId(Long bookId) {
        return bookMarkRepository.findUserIdsByBookId(bookId);
    }
}
