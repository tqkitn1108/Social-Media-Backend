package com.tqkien03.postservice.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentEventInfo {
    private Integer postId;
    private String userId;
    private LocalDateTime createdAt;
    private CommentEventType eventType;
}
