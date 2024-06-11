package com.tqkien03.commentservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MediaDto {
    private Integer id;
    private String mediaName;
    private String mediaType;
    private String url;
    private float height;
    private float width;
    private float size;
    private LocalDateTime createdAt;
    private Integer commentId;
    private String ownerId;
}
