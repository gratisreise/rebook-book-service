package com.example.rebookbookservice.domain.book.service;

import com.example.rebookbookservice.common.enums.Category;
import com.example.rebookbookservice.external.gemini.GeminiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiService {

  private final GeminiService geminiService;

  public String getCategory(String title) {
    String prompt = String.format("%s 중 %s의 분류는?? 분류명으로 대답\n", Category.allCategory(), title);
    return geminiService.callString(prompt);
  }
}
