package com.tqkien03.feedservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendDto {
    private String userId;
    private String fullName;
    private String avatarUrl;
    private boolean isFriend;
}
