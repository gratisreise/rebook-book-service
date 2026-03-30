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
        # Task
        이미지에서 도서를 식별하고, 표지에 나타난 도서명을 기반으로 해당 도서의 ISBN을 추론하라.
        
        # Rule
        1. 이미지에 보이는 도서 표지에서 도서명을 정확히 읽는다.
        2. 도서명이 불완전하거나 일부만 보일 경우, 문맥을 기반으로 가장 합리적인 전체 도서명을 추론한다.
        3. 식별된 도서명을 기반으로 실제 존재할 가능성이 높은 ISBN을 추론한다.
        4. 각 도서마다 ISBN은 최대 2개까지 제시한다.
        5. 결과는 반드시 지정된 JSON 구조로만 반환한다.
        
        # Constraint
        - 출력은 반드시 아래 JSON 형식만 사용한다.
        {
          "books": [
            {
              "title": "인식된 도서명",
              "isbns": ["978XXXXXXXXXX", "978YYYYYYYYYY"]
            }
          ]
        }
        - 도서를 식별할 수 없으면 다음과 같이 반환한다.
        {"books": []}
        - JSON 외의 추가 텍스트는 절대 포함하지 않는다.
        - ISBN은 13자리 문자열 형식만 허용한다.
      """;

  public String getCategory(String title) {
    String prompt = String.format("%s 중 %s의 분류는?? 분류명으로 대답\n", Category.allCategory(), title);
    return geminiService.callString(prompt);
  }

  public ExtractedBookInfo extractBookInfoFromImage(List<ImageSource> images) {
    return geminiService.callObjectWithImages(
        BOOK_ISBN_EXTRACTION_PROMPT, images, ExtractedBookInfo.class);
  }
}
