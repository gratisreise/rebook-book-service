package com.example.rebookbookservice.service;

import com.example.rebookbookservice.feigns.NaverClient;
import com.example.rebookbookservice.model.naver.NaverBooksResponse;
import com.google.genai.Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiService {
    private final NaverClient naverClient;

    @Value("${naver.client-id}")
    private String clientId;

    @Value("${naver.client-secret}")
    private String clientSecret;

    @Value("${gemini.apikey}")
    private String geminiKey;

    public NaverBooksResponse searchBooks(String keyword) {
        final int display = 100;
        return naverClient.searchBooks(keyword, display, clientId, clientSecret);
    }

    public String getCategory(String title){
        try (Client client = Client.builder().apiKey(geminiKey).build();){
            client.apiKey();
            String model = "gemini-2.0-flash-lite";
            String text = String.format(
                "소설, 자기계발, 어린이/청소년, IT/컴퓨터, 예술/문화, 경영/경제, 건강/취미 중 %s의 분류는?? 분류명으로 대답\n", title);
            return client.models.generateContent(model, text, null).text().trim();
        } catch(RuntimeException e) {
            log.error("Error occurred while gemini api running");
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
