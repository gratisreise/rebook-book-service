package com.example.rebookbookservice.repository;

import com.example.rebookbookservice.model.entity.Book;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findByTitleContaining(String keyword, Pageable pageable);
    Page<Book> findByCategoryIn(List<String> categories, Pageable pageable);
    List<Book> findByCategoryIn(List<String> categories);
    Book findByCategory(String category);

    boolean existsByIsbn(String isbn);
}