package com.tqkien03.reactservice.client;

import com.tqkien03.reactservice.dto.PostDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Optional;

@FeignClient(name = "post-service", url = "${application.config.post-url}")
public interface PostFeignClient {
    @GetMapping("/{id}")
    Optional<PostDto> getPost(@PathVariable Integer id, Authentication authentication);

    @PutMapping("/{postId}/react")
    public ResponseEntity<?> updateReaction(@PathVariable Integer postId);
}
