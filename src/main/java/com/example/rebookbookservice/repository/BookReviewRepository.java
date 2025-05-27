package com.example.rebookbookservice.repository;

import com.example.rebookbookservice.model.entity.BookReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookReviewRepository extends JpaRepository<BookReview, Long> {

    boolean existsByBookIdAndUserId(Long booId, String userId);

    Page<BookReview> findByBookId(Long bookId, Pageable pageable);
}
