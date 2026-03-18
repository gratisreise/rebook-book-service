package com.example.rebookbookservice.domain.book.service;

import com.example.rebookbookservice.common.enums.Category;
import com.example.rebookbookservice.domain.book.model.dto.response.ExtractedBookInfo;
import com.example.rebookbookservice.external.gemini.GeminiService;
import com.example.rebookbookservice.external.gemini.ImageSource;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiService {

  private final GeminiService geminiService;

  private static final String BOOK_ISBN_EXTRACTION_PROMPT =
      """
      이 이미지에서 도서를 식별하세요.

      지침:
      1. 이미지에 보이는 도서의 표지에서 도서명을 읽으세요
      2. 도서명을 기반으로 가장 적절한 ISBN을 추론하세요
      3. 각 도서마다 가장 가능성 높은 ISBN 후보를 최대 2개만 제공하세요

      다음 JSON 형식으로만 응답:
      {
        "books": [
          {
            "title": "인식된 도서명",
            "isbns": ["978XXXXXXXXXX", "978YYYYYYYYYY"]
          }
        ]
      }

      도서를 식별할 수 없으면: {"books": []}
      """;

  public String getCategory(String title) {
    String prompt = String.format("%s 중 %s의 분류는?? 분류명으로 대답\n", Category.allCategory(), title);
    return geminiService.callString(prompt);
  }

  public ExtractedBookInfo extractBookInfoFromImage(List<ImageSource> images) {
    return geminiService.callObjectWithImages(BOOK_ISBN_EXTRACTION_PROMPT, images, ExtractedBookInfo.class);
  }
}
