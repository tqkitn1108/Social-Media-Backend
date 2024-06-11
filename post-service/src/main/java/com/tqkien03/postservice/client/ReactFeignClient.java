package com.tqkien03.postservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "react-service", url = "${application.config.react-url}")
public interface ReactFeignClient {
    @GetMapping("/post/{postId}/by-user/{userId}")
    boolean getByPostAndUser(@PathVariable Integer postId,
                             @PathVariable("userId") String userId);
}
