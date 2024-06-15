package com.tqkien03.feedservice.client;

import com.tqkien03.feedservice.dto.FollowDto;
import com.tqkien03.feedservice.dto.FriendDto;
import com.tqkien03.feedservice.dto.UserDto;
import com.tqkien03.feedservice.dto.UserSummary;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "user-service", url = "${application.config.user-url}")
public interface UserFeignClient {

    @GetMapping("/{id}")
    UserDto getUserById(@PathVariable String id);
    @GetMapping("/summary-from-me/{userId}")
    Optional<UserSummary> getUserSummary(@PathVariable String userId, @RequestParam("from") String myId);

    @GetMapping("/{userId}/followers")
    List<FollowDto> getFollowers(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size);

    @GetMapping("/{userId}/followings")
    List<FollowDto> getFollowings(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size);

    @GetMapping("/{userId}/friends")
    List<FriendDto> getFriends(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size);

}
