package com.tqkien03.userservice.mapper;


import com.tqkien03.smcommon.dto.FollowDto;
import com.tqkien03.smcommon.dto.FriendDto;
import com.tqkien03.smcommon.dto.SimpleUserDto;
import com.tqkien03.smcommon.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    public FollowDto toFollowDto(User user, User me) {
        return FollowDto
                .builder()
                .userId(user.getId())
                .fullName(getFullName(user))
                .avatarUrl(user.getAvatarUrl())
                .isFollowing(isFollowing(user, me))
                .isFollower(isFollower(user, me))
                .build();
    }

    public FriendDto toFriendDto(User user, User me) {
        return FriendDto
                .builder()
                .userId(user.getId())
                .fullName(getFullName(user))
                .avatarUrl(user.getAvatarUrl())
                .isFriend(isFriend(user, me))
                .build();
    }

    public SimpleUserDto toSimpleUserDto(User user, User me) {
        return SimpleUserDto
                .builder()
                .id(user.getId())
                .fullName(getFullName(user))
                .avatarUrl(user.getAvatarUrl())
                .isFollowing(isFollowing(user, me))
                .isFollower(isFollower(user, me))
                .isFriend(isFriend(user, me))
                .followersCount(user.getActivity().getFollowersCount())
                .friendsCount(user.getActivity().getFriendsCount())
                .build();
    }


    public List<FollowDto> userListToFollowDtoList(List<User> followers, User me) {
        if (me == null) return null;
        return followers.stream()
                .map(follower -> toFollowDto(follower, me))
                .toList();
    }

    public List<FriendDto> userListToFriendDtoList(List<User> friends, User me) {
        if (me == null) return null;
        return friends.stream()
                .map(friend -> toFriendDto(friend, me))
                .toList();
    }

    public List<SimpleUserDto> usersToSimpleUserDtos(List<User> matchedUsers, User me) {
        return matchedUsers.stream().map(user -> toSimpleUserDto(user, me)).toList();
    }

    public String getFullName(User user) {
        return user.getUserProfile().getFullName();
    }

    boolean isFollower(User user, User me) {
        if (me == null) return false;
        return me.getFollowers().contains(user);
    }

    boolean isFollowing(User user, User me) {
        if (me == null) return false;
        return me.getFollowings().contains(user);
    }

    private boolean isFriend(User user, User me) {
        if (me == null) return false;
        return me.getFriends().contains(user);
    }

}
