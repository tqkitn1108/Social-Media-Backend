package com.tqkien03.commentservice.client;

import com.tqkien03.commentservice.dto.PostDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "post-service", url = "${application.config.post-url}")
public interface PostFeignClient {
    @GetMapping("/{id}")
    Optional<PostDto> getPost(@PathVariable Integer id, Authentication authentication);
}
