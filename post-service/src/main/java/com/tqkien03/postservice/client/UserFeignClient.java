package com.tqkien03.postservice.client;

import com.tqkien03.postservice.dto.UserSummary;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "user-service", url = "${application.config.user-url}")
public interface UserFeignClient {
    @GetMapping("/summary/{userId}")
    Optional<UserSummary> getUserSummary(@PathVariable String userId, Authentication authentication);
}
