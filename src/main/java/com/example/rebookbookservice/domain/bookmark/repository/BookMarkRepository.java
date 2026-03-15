package com.example.rebookbookservice.domain.bookmark.repository;

import com.example.rebookbookservice.domain.book.model.entity.Book;
import com.example.rebookbookservice.domain.bookmark.model.entity.BookMark;
import com.example.rebookbookservice.domain.bookmark.model.entity.compositekey.BookMarkId;
import java.util.List;
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

    @Query("SELECT bm.bookMarkId.userId FROM BookMark bm WHERE bm.bookMarkId.bookId = :bookId")
    List<String> findUserIdsByBookId(Long bookId);

}
