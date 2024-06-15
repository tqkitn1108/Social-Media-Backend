package com.tqkien03.userservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSummary {
    private String id;
    private String fullName;
    private String avatarUrl;
    private boolean isFollowing;
    private boolean isFollower;
    private boolean isFriend;
    private boolean isPending;
    private boolean isSendRequest;
    private Integer followersCount;
    private Integer followingsCount;
    private Integer friendsCount;
}
