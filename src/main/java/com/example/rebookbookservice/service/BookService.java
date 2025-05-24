package com.example.rebookbookservice.service;

import com.example.rebookbookservice.feigns.NaverClient;
import com.example.rebookbookservice.model.naver.NaverBooksResponse;
import com.example.rebookbookservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final NaverClient naverClient;

    @Value("${naver.client-id}")
    private String clientId;

    @Value("${naver.client-secret}")
    private String clientSecret;

    public NaverBooksResponse getNaverBooks(String keyword) {
        return naverClient.searchBooks(keyword, clientId, clientSecret);
    }
}
