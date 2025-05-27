package com.example.rebookbookservice.model.user;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorsRequest {
    List<String> userIds;

    public AuthorsRequest(List<String> userIds) {
        this.userIds = new ArrayList<>(userIds);
    }
}
