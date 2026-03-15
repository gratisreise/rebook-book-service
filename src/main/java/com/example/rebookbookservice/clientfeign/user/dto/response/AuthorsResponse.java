package com.example.rebookbookservice.clientfeign.user.dto.response;

import java.util.List;

public record AuthorsResponse(
    List<String> authors
) {
}
