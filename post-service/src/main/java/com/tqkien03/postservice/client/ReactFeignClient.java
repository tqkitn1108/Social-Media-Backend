package com.tqkien03.postservice.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "react-service", url = "${application.config.react-url}")
public interface ReactFeignClient {
    @GetMapping("/post/{postId}/by-user/{userId}")
    @CircuitBreaker(name = "react-service", fallbackMethod = "reactFallBack")
    boolean getByPostAndUser(@PathVariable Integer postId,
                             @PathVariable("userId") String userId);

    default boolean reactFallBack(Integer postId, String userId, Throwable throwable) {
        return false;
    }
}
