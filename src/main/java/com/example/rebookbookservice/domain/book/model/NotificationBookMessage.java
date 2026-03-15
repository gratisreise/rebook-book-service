package com.example.rebookbookservice.domain.book.model;

import java.io.Serializable;

public record NotificationBookMessage(
    String message,
    String type,
    String bookId,
    String category
) implements Serializable {
    public NotificationBookMessage(Long bookId, String category, String message) {
        this(message, "BOOK", bookId.toString(), category);
    }
}
