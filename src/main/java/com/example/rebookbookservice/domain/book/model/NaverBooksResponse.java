package com.example.rebookbookservice.domain.book.model;

import java.util.List;

public record NaverBooksResponse(
    String lastBuildDate,
    int total,
    int start,
    int display,
    List<Item> items
) {
}
