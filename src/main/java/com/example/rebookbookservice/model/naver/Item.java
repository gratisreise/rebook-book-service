package com.example.rebookbookservice.model.naver;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {
    private String title;
    private String link;
    private String author;
    private int price;
    private int discount;
    private String publisher;
    private String pubdate;
    private String isbn;
    private String description;
}