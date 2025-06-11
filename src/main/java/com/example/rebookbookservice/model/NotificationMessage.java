package com.example.rebookbookservice.model;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationMessage implements Serializable {
    @NotBlank
    private String userId;

    @NotBlank
    private String content;

    @NotBlank
    private String type;

    @NotBlank
    private String relatedId;


    public void makeMessage(Long id, String category) {
        this.userId = "bookIsEmpty";
        this.content = String.format("%s 카테고리에 새로운 도서가 등록되었습니다.", category);
        this.type = "BOOK";
        this.relatedId = String.valueOf(id);
    }
}
