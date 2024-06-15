package com.tqkien03.commentservice.client;

import com.tqkien03.commentservice.dto.UserSummary;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "user-service", url = "${application.config.user-url}")
public interface UserFeignClient {
    @GetMapping("/summary-from-me/{userId}")
    @CircuitBreaker(name = "user-service", fallbackMethod = "userFallBack")
    UserSummary getUserSummary(@PathVariable String userId, @RequestParam("from") String myId);

    default UserSummary userFallBack(String userId, String myId, Throwable throwable) {
        return UserSummary.builder().fullName("Social Media User")
                .avatarUrl("https://res.cloudinary.com/dxwdkeign/image/upload/v1718177786/qy79yhrfgenypywfaznb.jpg")
                .build();
    }
}
