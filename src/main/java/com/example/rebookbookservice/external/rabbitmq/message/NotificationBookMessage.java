package com.example.rebookbookservice.external.rabbitmq.message;

import java.io.Serializable;
import lombok.Builder;

@Builder
public record NotificationBookMessage(String message, String type, String bookId, String category)
    implements Serializable {

  public static NotificationBookMessage of(Long bookId, String category, String message) {
    return NotificationBookMessage.builder()
        .message(message)
        .type("BOOK")
        .bookId(bookId.toString())
        .category(category)
        .build();
  }
}
