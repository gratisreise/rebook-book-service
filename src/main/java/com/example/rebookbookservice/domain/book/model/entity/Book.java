package com.example.rebookbookservice.domain.book.model.entity;

import com.example.rebookbookservice.common.enums.Category;
import com.example.rebookbookservice.domain.book.model.dto.request.BookRequest;
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
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
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

    @Column(nullable = false, length = 20) // 한글은 UTF-8 기준 바이트 수가 크므로 길이를 넉넉히 잡는 게 좋습니다.
    @Enumerated(EnumType.STRING) // 숫자가 아닌 문자열(상수명) 그대로 DB에 저장
    private Category category;

    @Column(nullable = false)
    private float rating;

    @Column(nullable = false)
    private Integer price;

    public Book(long bookId){
        this.id = bookId;
    }
}
