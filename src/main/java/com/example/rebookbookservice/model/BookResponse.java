package com.example.rebookbookservice.model;

import com.example.rebookbookservice.model.entity.Book;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {
    private Long bookId;
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private String description;
    private LocalDate publishedDate;
    private String cover;
    private String category;
    private float rating;
    private Integer price;
    private boolean isMarked;

    public BookResponse(Book book) {
        this.bookId = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.publisher = book.getPublisher();
        this.isbn = book.getIsbn();
        this.description = book.getDescription();
        this.publishedDate = book.getPublishedDate();
        this.cover = book.getCover();
        this.category = book.getCategory();
        this.rating = book.getRating();
        this.price = book.getPrice();
    }
}
