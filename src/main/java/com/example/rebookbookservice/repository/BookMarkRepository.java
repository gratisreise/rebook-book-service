package com.example.rebookbookservice.repository;

import com.example.rebookbookservice.model.entity.Book;
import com.example.rebookbookservice.model.entity.BookMark;
import com.example.rebookbookservice.model.entity.compositekey.BookMarkId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookMarkRepository extends JpaRepository<BookMark, BookMarkId> {
    @Query(
        value = "SELECT bm.book FROM BookMark bm WHERE bm.bookMarkId.userId = :userId",
        countQuery = "SELECT COUNT(bm) FROM BookMark bm WHERE bm.bookMarkId.userId = :userId"
    )
    Page<Book> findBooksBookmarkedByUser(String userId, Pageable pageable);
    boolean existsByBookMarkId(BookMarkId bookMarkId);

    BookMarkId book(Book book);
}
