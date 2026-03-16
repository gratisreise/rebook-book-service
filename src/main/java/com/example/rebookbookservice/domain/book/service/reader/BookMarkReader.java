package com.example.rebookbookservice.domain.book.service.reader;

import com.example.rebookbookservice.domain.book.model.entity.Book;
import com.example.rebookbookservice.domain.book.model.entity.compositekey.BookMarkId;
import com.example.rebookbookservice.domain.book.repository.BookMarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookMarkReader {
  private final BookMarkRepository bookMarkRepository;

  public boolean existsByBookMarkId(BookMarkId bookMarkId) {
    return bookMarkRepository.existsByBookMarkId(bookMarkId);
  }

  public Page<Book> getMarkedBooks(String userId, Pageable pageable) {
    return bookMarkRepository.findBooksBookmarkedByUser(userId, pageable);
  }

  public boolean existsById(BookMarkId bookMarkId) {
    return bookMarkRepository.existsById(bookMarkId);
  }
}
