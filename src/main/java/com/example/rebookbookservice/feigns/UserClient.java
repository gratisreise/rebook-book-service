package com.example.rebookbookservice.feigns;

import com.example.rebookbookservice.model.user.AuthorsRequest;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="user-service", url="http://localhost:9000/api/users")
public interface UserClient {
    //유저 닉네임 가져오기
    @PostMapping("/authors")
    List<String> getUser(@RequestBody AuthorsRequest request);

    @GetMapping("/categories/recommendations/{userId}")
    List<String> getFavoriteCategories(@PathVariable String userId);

    //해당 카테고리 선호 유저들
    @GetMapping("/categories/favorites")
    List<String> getUserByCategory(@RequestParam String category);
}
