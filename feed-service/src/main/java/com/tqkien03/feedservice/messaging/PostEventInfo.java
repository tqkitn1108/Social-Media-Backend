package com.tqkien03.feedservice.messaging;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostEventInfo {
    private Integer postId;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private String ownerId;
    private PostEventType eventType;
}
