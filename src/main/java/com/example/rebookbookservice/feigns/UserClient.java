package com.example.rebookbookservice.feigns;

import com.example.rebookbookservice.model.user.AuthorsRequest;
import com.example.rebookbookservice.model.user.AuthorsResponse;
import com.example.rebookbookservice.model.user.UserResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="user-service", url="http://localhost:9000")
public interface UserClient {
    //유저 닉네임 가져오기
    @PostMapping("/api/users/authors")
    List<String> getUser(@RequestBody AuthorsRequest request);
}
