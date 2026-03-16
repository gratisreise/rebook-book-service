package com.example.rebookbookservice.domain.book.service.writer;

import com.example.rebookbookservice.domain.book.model.entity.Book;
import com.example.rebookbookservice.domain.book.model.entity.BookMark;
import com.example.rebookbookservice.domain.book.model.entity.compositekey.BookMarkId;
import com.example.rebookbookservice.domain.book.repository.BookMarkRepository;
import com.example.rebookbookservice.domain.book.service.reader.BookMarkReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookMarkWriter {
    private final BookMarkRepository bookMarkRepository;
    private final BookMarkReader bookMarkReader;

    public void markingToggle(String userId, Long bookId) {
        BookMarkId bookMarkId = new BookMarkId(bookId, userId);
        if (bookMarkReader.existsById(bookMarkId)) {
            bookMarkRepository.deleteById(bookMarkId);
        } else {
            bookMarkRepository.save(new BookMark(bookMarkId, new Book(bookId)));
        }
    }
}
