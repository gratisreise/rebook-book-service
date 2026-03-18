package com.example.rebookbookservice.domain.book.model.dto.response;

import java.util.List;

public record ExtractedBookInfo(List<BookIsbnCandidate> books) {
  public record BookIsbnCandidate(
      String title, // 인식된 도서명
      List<String> isbns // 후보 ISBN (최대 2개)
      ) {}
}
