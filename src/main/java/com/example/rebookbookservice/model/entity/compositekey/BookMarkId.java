package com.example.rebookbookservice.model.entity.compositekey;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class BookMarkId {
    private Long bookId;
    private String userId;
}
