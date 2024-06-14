package com.tqkien03.feedservice.controller;

import com.tqkien03.feedservice.dto.PostDto;
import com.tqkien03.feedservice.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/feeds")
@RequiredArgsConstructor
public class FeedController {
    private final FeedService feedService;
    @GetMapping
    public ResponseEntity<List<PostDto>> getFeed(Authentication authentication,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        List<PostDto> postDtos = feedService.getUserFeed(authentication, page, size);
        return postDtos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(postDtos);
    }
}
