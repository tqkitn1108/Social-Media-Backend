package com.tqkien03.commentservice.messaging;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentEventInfo {
    private Integer postId;
    private String userId;
    private LocalDateTime createdAt;
    private CommentEventType eventType;
}
