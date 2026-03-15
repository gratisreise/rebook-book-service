package com.example.rebookbookservice.domain.book.model.entity;

import com.example.rebookbookservice.domain.book.model.BookRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 50)
    private String author;

    @Column(nullable = false, length = 30)
    private String publisher;

    @Column(nullable = false)
    private LocalDate publishedDate;

    @Column(length = 30)
    private String isbn;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false, length = 400)
    private String cover;

    @Column(nullable = false, length = 10)
    private String category;

    @Column(nullable = false)
    private float rating;

    @Column(nullable = false)
    private Integer price;

    public Book(BookRequest request, String category, LocalDate publishedDate) {
        this.title = request.title();
        this.author = request.author();
        this.publisher = request.publisher();
        this.publishedDate = publishedDate;
        this.isbn = request.isbn();
        this.description = request.description();
        this.cover = request.cover();
        this.category = category;
        this.price = request.price() != null ? request.price() : 0;
    }

    public Book(long bookId){
        this.id = bookId;
    }
}
