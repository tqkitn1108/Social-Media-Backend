package com.tqkien03.commentservice.client;

import com.tqkien03.commentservice.dto.UserSummary;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "user-service", url = "${application.config.user-url}")
public interface UserFeignClient {
    @GetMapping("/summary-from-me/{userId}")
    Optional<UserSummary> getUserSummary(@PathVariable String userId, @RequestParam("from") String myId);
}
