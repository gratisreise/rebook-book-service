package com.example.rebookbookservice.domain.book.model.entity;

import com.example.rebookbookservice.common.enums.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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

  @Column(nullable = false, length = 20)
  @Enumerated(EnumType.STRING)
  private Category category;

  @Column(nullable = false)
  private float rating;

  @Column(nullable = false)
  private Integer price;

  public Book(long bookId) {
    this.id = bookId;
  }
}
