package com.tqkien03.userservice.mapper;


import com.tqkien03.userservice.dto.FollowDto;
import com.tqkien03.userservice.dto.FriendDto;
import com.tqkien03.userservice.dto.UserSummary;
import com.tqkien03.userservice.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    public FollowDto toFollowDto(User user, User me) {
        return FollowDto
                .builder()
                .id(user.getId())
                .fullName(getFullName(user))
                .avatarUrl(user.getAvatarUrl())
                .isFollowing(isFollowing(user, me))
                .isFollower(isFollower(user, me))
                .build();
    }

    public FriendDto toFriendDto(User user, User me) {
        return FriendDto
                .builder()
                .id(user.getId())
                .fullName(getFullName(user))
                .avatarUrl(user.getAvatarUrl())
                .isFriend(isFriend(user, me))
                .build();
    }

    public UserSummary toUserSummary(User user, User me) {
        return UserSummary
                .builder()
                .id(user.getId())
                .fullName(getFullName(user))
                .avatarUrl(user.getAvatarUrl())
                .isFollowing(isFollowing(user, me))
                .isFollower(isFollower(user, me))
                .isFriend(isFriend(user, me))
                .isPending(isPending(user, me))
                .isSendRequest(isSendRequest(user, me))
                .followingsCount(user.getFollowingsCount())
                .followersCount(user.getFollowersCount())
                .friendsCount(user.getFriendsCount())
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

    public List<UserSummary> usersToUserSummaries(List<User> matchedUsers, User me) {
        return matchedUsers.stream().map(user -> toUserSummary(user, me)).toList();
    }

    public String getFullName(User user) {
        return user.getProfile().getFullName();
    }

    boolean isFollower(User user, User me) {
        if (me.getFollowers() == null) return false;
        return me.getFollowers().contains(user);
    }

    boolean isFollowing(User user, User me) {
        if (me.getFollowings() == null) return false;
        return me.getFollowings().contains(user);
    }

    private boolean isFriend(User user, User me) {
        return me.getFriends().contains(user);
    }

    private boolean isSendRequest(User user, User me) {
        if (me.getFriendRequests() == null) return false;
        return me.getFriendRequests().contains(user);
    }

    private boolean isPending(User user, User me) {
        if (me.getFriendPendings() == null) return false;
        return me.getFriendPendings().contains(user);
    }

}
