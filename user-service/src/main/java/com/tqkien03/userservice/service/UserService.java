package com.tqkien03.userservice.service;

import com.tqkien03.userservice.config.KeyCloakJwtAuthenticationConverter;
import com.tqkien03.userservice.dto.FollowDto;
import com.tqkien03.userservice.dto.FriendDto;
import com.tqkien03.userservice.dto.ProfileDto;
import com.tqkien03.userservice.dto.UserSummary;
import com.tqkien03.userservice.exception.ResourceNotFoundException;
import com.tqkien03.userservice.mapper.UserMapper;
import com.tqkien03.userservice.model.User;
import com.tqkien03.userservice.model.Profile;
import com.tqkien03.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User getCurrentUser(String id) {
        if (userRepository.findById(id).isPresent()) {
            return userRepository.findById(id).get();
        }
        User user = User.builder().id(id).build();
        Profile profile = KeyCloakJwtAuthenticationConverter.getUserProfile();

        user.setProfile(profile);
        userRepository.save(user);
        return user;
    }

    public void updateProfile(ProfileDto profileDto, String id) {
        Profile profile = profileDto.toProfile();
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        user.setProfile(profile);

        userRepository.save(user);
    }

    public Object getUserSummary(String targetId, String id) {
        User me = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        User target = userRepository.findById(targetId)
                .orElseThrow(() -> new ResourceNotFoundException(targetId));
        return userMapper.toUserSummary(target, me);
    }

    public void updateAvatar(Map<String, String> updates, String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        user.setAvatarUrl(updates.get("avatarUrl"));
        userRepository.save(user);
    }


    public List<FollowDto> getFollowers(String targetId, String myId, int page, int size) {
        User me = userRepository.findById(myId).orElseThrow(() -> new ResourceNotFoundException(myId));
        List<String> followerIds = userRepository.findFollowersByUserId(targetId, PageRequest.of(page, size));
        List<User> followers = userRepository.findAllById(followerIds);
        return userMapper.userListToFollowDtoList(followers, me);
    }

    public List<FollowDto> getFollowings(String targetId, String myId, int page, int size) {
        User me = userRepository.findById(myId).orElseThrow(() -> new ResourceNotFoundException(myId));
        List<String> followingIds = userRepository.findFollowingsByUserId(targetId, PageRequest.of(page, size));
        List<User> followings = userRepository.findAllById(followingIds);
        return userMapper.userListToFollowDtoList(followings, me);
    }

    public List<FriendDto> getFriends(String targetId, String myId, int page, int size) {
        User me = userRepository.findById(myId).orElseThrow(() -> new ResourceNotFoundException(myId));
        List<String> friendIds = userRepository.findFriendsByUserId(targetId, PageRequest.of(page, size));
        List<User> friends = userRepository.findAllById(friendIds);
        return userMapper.userListToFriendDtoList(friends, me);
    }

    public boolean follow(String targetId, String myId) {
        User me = userRepository.findById(myId).orElseThrow(() -> new ResourceNotFoundException(myId));
        User target = userRepository.findById(targetId)
                .orElseThrow(() -> new ResourceNotFoundException(targetId));

        target.getFollowers().add(me);
        me.getFollowings().add(target);

        userRepository.save(me);
        userRepository.save(target);

        return true;
    }

    public boolean unfollow(String targetId, String myId) {
        User me = userRepository.findById(myId).orElseThrow(() -> new ResourceNotFoundException(myId));
        User target = userRepository.findById(targetId)
                .orElseThrow(() -> new ResourceNotFoundException(targetId));

        if (!me.getFollowings().contains(target)) return false;

        target.getFollowers().remove(me);
        me.getFollowings().remove(target);

        userRepository.save(me);
        userRepository.save(target);

        return true;
    }

    public boolean addFriend(String targetId, String myId) {
        User me = userRepository.findById(myId).orElseThrow(() -> new ResourceNotFoundException(myId));
        User target = userRepository.findById(targetId)
                .orElseThrow(() -> new ResourceNotFoundException(targetId));

        target.getFriendPendings().add(me);
        me.getFriendRequests().add(target);

        userRepository.save(me);
        userRepository.save(target);

        return true;
    }

    public boolean acceptFriendRequest(String targetId, String myId) {
        User me = userRepository.findById(myId).orElseThrow(() -> new ResourceNotFoundException(myId));
        User target = userRepository.findById(targetId)
                .orElseThrow(() -> new ResourceNotFoundException(targetId));

        if (!me.getFriendPendings().contains(target) || !target.getFriendRequests().contains(me)) return false;

        target.getFriends().add(me);
        me.getFriends().add(target);

        userRepository.save(me);
        userRepository.save(target);

        return true;
    }

    public boolean unfriend(String targetId, String myId) {
        User me = userRepository.findById(myId).orElseThrow(() -> new ResourceNotFoundException(myId));
        User target = userRepository.findById(targetId)
                .orElseThrow(() -> new ResourceNotFoundException(targetId));

        if (!me.getFriends().contains(target) || !target.getFriends().contains(me)) return false;

        target.getFriends().remove(me);
        me.getFriends().remove(target);

        userRepository.save(me);
        userRepository.save(target);

        return true;
    }

    public List<UserSummary> searchUser(String key, String myId) {
        User me = userRepository.findById(myId).orElseThrow(() -> new ResourceNotFoundException(myId));
        List<User> matchedUsers = userRepository.findByProfile_FullNameContainingIgnoreCase(key);
        matchedUsers.remove(me);
        return userMapper.usersToUserSummaries(matchedUsers, me);
    }

    public UserSummary searchUserById(String searchId, String myId) {
        User me = userRepository.findById(myId).orElseThrow(() -> new ResourceNotFoundException(myId));
        Optional<User> target = userRepository.findById(searchId);
        return target.map(user -> userMapper.toUserSummary(user, me)).orElse(null);
    }
}
