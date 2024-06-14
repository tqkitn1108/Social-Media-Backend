package com.tqkien03.userservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FollowDto {
    private String id;
    private String fullName;
    private String avatarUrl;
    private boolean isFollowing;
    private boolean isFollower;
}
