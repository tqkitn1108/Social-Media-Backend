package com.tqkien03.postservice.messaging;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class PostEventInfo {
    private Integer postId;
    private String ownerId;
    private PostEventType eventType;
}
