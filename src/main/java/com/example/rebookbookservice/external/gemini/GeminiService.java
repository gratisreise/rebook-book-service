package com.example.rebookbookservice.external.gemini;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.rebook.common.core.exception.BusinessException;
import com.rebook.common.core.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeminiService {

  private final Client client;

  public String callString(String prompt) {
    String model = "gemini-2.5-flash";
    try {
      GenerateContentResponse response = client.models.generateContent(model, prompt, null);
      return response.text();
    } catch (Exception e) {
      throw new BusinessException(ErrorCode.EXTERNAL_API_ERROR);
    }
  }
}
