package com.example.rebookbookservice.feigns;

import com.example.rebookbookservice.model.naver.NaverBooksResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="naverApi", url = "https://openapi.naver.com")
public interface NaverClient {
    @GetMapping("/v1/search/book.json")
    NaverBooksResponse searchBooks(
        @RequestParam("query") String query,
        @RequestHeader("X-Naver-Client-Id") String clientId,
        @RequestHeader("X-Naver-Client-Secret") String clientSecret
    );
}
