package com.tqkien03.commentservice.dto;

import com.tqkien03.commentservice.model.Media;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PostDto {
    private Integer id;
    private String ownerId;
    private String content;
    private List<Media> medias;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private int reactsCount;
    private int commentsCount;
    private UserSummary user;
}
