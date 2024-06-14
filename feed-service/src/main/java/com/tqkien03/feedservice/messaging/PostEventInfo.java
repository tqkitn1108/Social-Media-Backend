package com.tqkien03.feedservice.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostEventInfo {
    private Integer postId;
    private String ownerId;
    private PostEventType eventType;
}
