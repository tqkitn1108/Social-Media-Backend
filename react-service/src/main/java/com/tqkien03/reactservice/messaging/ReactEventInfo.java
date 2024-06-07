package com.tqkien03.reactservice.messaging;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReactEventInfo {
    private Integer postId;
    private String userId;
    private LocalDateTime createdAt;
    private ReactEventType eventType;
}
