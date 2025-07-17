package com.example.rebookbookservice.model.naver;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaverBooksResponse {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<Item> items;



}
