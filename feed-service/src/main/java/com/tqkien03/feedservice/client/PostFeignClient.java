package com.tqkien03.feedservice.client;

import com.tqkien03.feedservice.dto.PostDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "post-service", url = "${application.config.post-url}")
public interface PostFeignClient {
    @GetMapping("/by-id/{id}")
    PostDto getPost(@PathVariable Integer id, @RequestParam String myId);
}
