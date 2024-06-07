package com.tqkien03.feedservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FollowDto {
    private String userId;
    private String fullName;
    private String avatarUrl;
    private boolean isFollowing;
    private boolean isFollower;
}
