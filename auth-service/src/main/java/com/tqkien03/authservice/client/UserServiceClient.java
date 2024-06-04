package com.tqkien03.authservice.client;

import com.tqkien03.authservice.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(name = "user-service", url = "${application.config.users-url}")
public interface UserServiceClient {
    @GetMapping("/get-user-by-email/{email}")
    Optional<User> getUserByEmail(@PathVariable String email);
}
