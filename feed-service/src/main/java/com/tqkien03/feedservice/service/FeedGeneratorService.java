package com.tqkien03.feedservice.service;

import com.tqkien03.feedservice.client.UserFeignClient;
import com.tqkien03.feedservice.dto.FollowDto;
import com.tqkien03.feedservice.dto.FriendDto;
import com.tqkien03.feedservice.messaging.PostEventInfo;
import com.tqkien03.feedservice.model.Feed;
import com.tqkien03.feedservice.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedGeneratorService {
    private final UserFeignClient userFeignClient;
    private final FeedRepository feedRepository;

    public void addToFeed(PostEventInfo postEventInfo) {
        int postId = postEventInfo.getPostId();
        String userId = postEventInfo.getOwnerId();
        int page = 0, size = 10;
        List<FollowDto> followers = userFeignClient.getFollowers(userId, page, size);
        List<FriendDto> friends = userFeignClient.getFriends(userId, page, size);
        if(followers != null && !followers.isEmpty()){
            followers.forEach(follower -> {
                Feed followerFeed = feedRepository.findByUserId(follower.getUserId());
                followerFeed.getPostIds().add(postId);
                feedRepository.save(followerFeed);
            });
        }
        if(friends != null && !friends.isEmpty()){
            friends.forEach(friend -> {
                Feed friendFeed = feedRepository.findByUserId(friend.getUserId());
                friendFeed.getPostIds().add(postId);
                feedRepository.save(friendFeed);
            });
        }
    }

    public void deleteFromFeed(PostEventInfo postEventInfo) {
        int postId = postEventInfo.getPostId();
        List<Feed> feeds = feedRepository.findByPostId(postId);
        List<Feed> updatedFeeds = feeds.stream().peek(feed -> feed.getPostIds().remove(postId)).toList();
        updatedFeeds.forEach(feedRepository::save);
    }
}
