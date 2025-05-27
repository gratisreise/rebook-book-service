package com.example.rebookbookservice.feigns;

import com.example.rebookbookservice.model.user.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="user-service", url="http://localhost:9000")
public interface UserClient {
    //유저 닉네임 가져오기
    @GetMapping("/api/users/{userId}")
    UserResponse getUser(@PathVariable String userId);
}
