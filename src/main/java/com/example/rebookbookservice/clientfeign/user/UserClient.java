package com.example.rebookbookservice.clientfeign.user;

import com.example.rebookbookservice.clientfeign.user.dto.request.AuthorsRequest;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", path = "/internal/users")
public interface UserClient {
  @PostMapping("/authors")
  List<String> getUser(@RequestBody AuthorsRequest request);

  @GetMapping("/categories/recommendations/{userId}")
  List<String> getFavoriteCategories(@PathVariable String userId);

  @GetMapping("/categories/favorites")
  List<String> getUserByCategory(@RequestParam String category);
}
