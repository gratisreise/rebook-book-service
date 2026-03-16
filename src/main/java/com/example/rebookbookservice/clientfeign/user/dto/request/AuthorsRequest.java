package com.example.rebookbookservice.clientfeign.user.dto.request;

import java.util.ArrayList;
import java.util.List;

public record AuthorsRequest(List<String> userIds) {
  public AuthorsRequest {
    userIds = new ArrayList<>(userIds);
  }
}
