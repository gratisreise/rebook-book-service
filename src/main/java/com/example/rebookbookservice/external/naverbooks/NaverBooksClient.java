package com.example.rebookbookservice.external.naverbooks;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class NaverBooksClient {
    private final RestClient restClient;
}
