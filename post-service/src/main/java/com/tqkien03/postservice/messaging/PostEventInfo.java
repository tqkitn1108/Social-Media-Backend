package com.tqkien03.postservice.messaging;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostEventInfo {
    private Integer postId;
    private String ownerId;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private PostEventType eventType;
}
