package com.tqkien03.reactservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostDto {
    private Integer id;
    private String ownerId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private int reactsCount;
    private int commentsCount;
    private UserSummary user;
}
