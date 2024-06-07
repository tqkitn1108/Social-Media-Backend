package com.tqkien03.feedservice.client;

import com.tqkien03.feedservice.dto.PostDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "post-service", url = "${application.config.post-url}")
public interface PostFeignClient {
    @GetMapping("/{id}")
    PostDto getPost(@PathVariable Integer id, Authentication authentication);
}
