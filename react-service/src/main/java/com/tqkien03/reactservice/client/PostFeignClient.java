package com.tqkien03.reactservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "post-service", url = "${application.config.post-url}")
public interface PostFeignClient {
    @GetMapping("/exist/{id}")
    boolean checkPostExist(@PathVariable Integer id);
}
