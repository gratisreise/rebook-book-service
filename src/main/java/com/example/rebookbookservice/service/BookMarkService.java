package com.example.rebookbookservice.service;

import com.example.rebookbookservice.common.PageResponse;
import com.example.rebookbookservice.model.entity.Book;
import com.example.rebookbookservice.model.entity.BookMark;
import com.example.rebookbookservice.model.entity.compositekey.BookMarkId;
import com.example.rebookbookservice.repository.BookMarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookMarkService {
    private final BookMarkRepository bookMarkRepository;

    @Transactional
    public void markingToggle(String userId, Long bookId) {
        BookMarkId bookMarkId = new BookMarkId(bookId, userId);
        if (bookMarkRepository.existsById(bookMarkId)) {
            bookMarkRepository.deleteById(bookMarkId);
        } else {
            bookMarkRepository.save(new BookMark(bookMarkId, new Book(bookId)));
        }
    }


    public PageResponse<Book> getMarkedBooks(String userId, Pageable pageable) {
        return new PageResponse<>(bookMarkRepository.findBooksBookmarkedByUser(userId, pageable));
    }
}
