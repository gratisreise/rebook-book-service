package com.example.rebookbookservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

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
}
