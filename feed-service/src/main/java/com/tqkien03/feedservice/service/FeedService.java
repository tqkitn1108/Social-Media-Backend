package com.tqkien03.feedservice.service;

import com.tqkien03.feedservice.client.PostFeignClient;
import com.tqkien03.feedservice.dto.PostDto;
import com.tqkien03.feedservice.model.Feed;
import com.tqkien03.feedservice.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedRepository feedRepository;
    private final PostFeignClient postFeignClient;

    public List<PostDto> getUserFeed(Authentication authentication, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Feed feed = feedRepository.findByUserId(authentication.getName());
        return feed.getPostIds().stream()
                .map(postId -> postFeignClient.getPost(postId, authentication)).toList();
    }
}
