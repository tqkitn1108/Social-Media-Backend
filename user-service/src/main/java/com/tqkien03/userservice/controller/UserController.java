package com.tqkien03.userservice.controller;

import com.tqkien03.smcommon.dto.FollowDto;
import com.tqkien03.smcommon.dto.FriendDto;
import com.tqkien03.smcommon.model.User;
import com.tqkien03.smcommon.model.UserProfile;
import com.tqkien03.smcommon.dto.ProfileDto;
import com.tqkien03.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        User user = service.getCurrentUser(authentication.getName());
        return ResponseEntity.ok(user);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfile> getProfile(Authentication authentication) {
        return ResponseEntity.ok(service.getProfile(authentication.getName()));
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<List<FollowDto>> getFollowers(
            @PathVariable String userId, Authentication authentication,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        List<FollowDto> followers = service.getFollowers(userId, authentication.getName(), page, size);
        return !followers.isEmpty() ? ResponseEntity.ok(followers) : ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/followings")
    public ResponseEntity<List<FollowDto>> getFollowings(
            @PathVariable String userId, Authentication authentication,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        List<FollowDto> followings = service.getFollowings(userId, authentication.getName(), page, size);
        return !followings.isEmpty() ? ResponseEntity.ok(followings) : ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/friends")
    public ResponseEntity<List<FriendDto>> getFriends(
            @PathVariable String userId, Authentication authentication,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        List<FriendDto> friends = service.getFriends(userId, authentication.getName(), page, size);
        return !friends.isEmpty() ? ResponseEntity.ok(friends) : ResponseEntity.noContent().build();
    }

    @PostMapping("/add-friend/{id}")
    public ResponseEntity<?> addFriend(@PathVariable String id, Authentication authentication) {
        if (service.addFriend(id, authentication.getName()))
            return ResponseEntity.ok("Send friend request successfully");
        return ResponseEntity.badRequest().body("cannot follow");
    }

    @PostMapping("/accept-friend/{id}")
    public ResponseEntity<?> acceptFriendRequest(@PathVariable String id, Authentication authentication) {
        if (service.acceptFriendRequest(id, authentication.getName()))
            return ResponseEntity.ok("Accept friend request successfully");
        return ResponseEntity.badRequest().body("Failed");
    }

    @PostMapping("/follow/{id}")
    public ResponseEntity<?> follow(@PathVariable String id, Authentication authentication) {
        if (service.follow(id, authentication.getName()))
            return ResponseEntity.ok("Follow successfully");
        return ResponseEntity.badRequest().body("cannot follow");
    }

    @PostMapping("/unfollow/{id}")
    public ResponseEntity<?> unfollow(@PathVariable String id, Authentication authentication) {
        if (service.unfollow(id, authentication.getName()))
            return ResponseEntity.ok("Unfollow successfully");
        return ResponseEntity.badRequest().body("cannot unfollow");
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody ProfileDto profileDto, Authentication authentication) {
        service.updateProfile(profileDto, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/avatar")
    public ResponseEntity<?> updateAvatar(@RequestBody Map<String, String> updates, Authentication authentication) {
        service.updateAvatar(updates, authentication.getName());
        return ResponseEntity.ok().build();
    }

}
