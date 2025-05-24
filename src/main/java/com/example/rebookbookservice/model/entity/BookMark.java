package com.example.rebookbookservice.model.entity;

import com.example.rebookbookservice.model.entity.compositekey.BookMarkId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BookMark {
    @EmbeddedId
    BookMarkId bookMarkId;
}
