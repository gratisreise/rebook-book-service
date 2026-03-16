package com.example.rebookbookservice.external.naverbooks;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class NaverBooksClient {
    private final RestClient restClient;

    @Value("${naver.client-id}")
    private String clientId;

    @Value("${naver.client-secret}")
    private String clientSecret;

    private static final String NAVER_BOOK_API_URL = "https://openapi.naver.com/v1/search/book.json";

    public NaverBooksResponse searchBooksByTitle(String title, int display) {
        return restClient.get()
            .uri(NAVER_BOOK_API_URL, uriBuilder -> uriBuilder
                .queryParam("query", title)
                .queryParam("display", display)
                .build())
            .header("X-Naver-Client-Id", clientId)
            .header("X-Naver-Client-Secret", clientSecret)
            .retrieve()
            .body(NaverBooksResponse.class);
    }
}
