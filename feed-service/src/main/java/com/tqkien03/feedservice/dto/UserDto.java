package com.tqkien03.feedservice.dto;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class UserDto {
    private String id;
    private String avatarUrl;
    private String fullName;
    private String email;
}
