package com.example.rebookbookservice.external.naverbooks;

import java.util.List;

public record NaverBooksResponse(
    String lastBuildDate,
    int total,
    int start,
    int display,
    List<Item> items
) {

    public static record Item(
        String title,
        String link,
        String author,
        String image,
        String discount,
        String publisher,
        String pubdate,
        String isbn,
        String description
    ) {
    }
}
