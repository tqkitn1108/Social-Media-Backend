package com.tqkien03.commentservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CommentDto {
    private Integer id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private List<MediaDto> medias;
    private Integer postId;
    private UserSummary user;
}
