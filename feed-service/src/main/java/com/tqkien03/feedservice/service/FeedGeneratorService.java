package com.tqkien03.feedservice.service;

import com.tqkien03.feedservice.client.UserFeignClient;
import com.tqkien03.feedservice.dto.FollowDto;
import com.tqkien03.feedservice.dto.FriendDto;
import com.tqkien03.feedservice.exception.ResourceNotFoundException;
import com.tqkien03.feedservice.messaging.PostEventInfo;
import com.tqkien03.feedservice.model.Feed;
import com.tqkien03.feedservice.model.Post;
import com.tqkien03.feedservice.repository.FeedRepository;
import com.tqkien03.feedservice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FeedGeneratorService {
    private final UserFeignClient userFeignClient;
    private final FeedRepository feedRepository;
    private final PostRepository postRepository;

    public void addToFeed(PostEventInfo postEventInfo) {
        int postId = postEventInfo.getPostId();
        String userId = postEventInfo.getOwnerId();
        Post post = Post.builder().id(postId).feeds(new HashSet<>()).build();
        int page = 0, size = 10;
        List<FollowDto> followers = userFeignClient.getFollowers(userId, page, size);
        List<FriendDto> friends = userFeignClient.getFriends(userId, page, size);

        Feed myFeed = feedRepository.findByUserId(userId);
        post.getFeeds().add(myFeed);
        if(followers != null && !followers.isEmpty()){
            followers.forEach(follower -> {
                Feed followerFeed = feedRepository.findByUserId(follower.getId());
                post.getFeeds().add(followerFeed);
            });
        }
        if(friends != null && !friends.isEmpty()){
            friends.forEach(friend -> {
                Feed friendFeed = feedRepository.findByUserId(friend.getId());
                post.getFeeds().add(friendFeed);
            });
        }
        postRepository.save(post);
    }

    public void deleteFromFeed(PostEventInfo postEventInfo) {
        int postId = postEventInfo.getPostId();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(postId)));
        Set<Feed> feeds = post.getFeeds();
        List<Feed> updatedFeeds = feeds.stream().peek(feed -> feed.getPosts().remove(post)).toList();
        postRepository.delete(post);
        updatedFeeds.forEach(feedRepository::save);
    }
}
