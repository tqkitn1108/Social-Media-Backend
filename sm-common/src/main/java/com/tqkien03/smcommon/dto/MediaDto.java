package com.tqkien03.smcommon.dto;

import com.tqkien03.smcommon.model.Comment;
import com.tqkien03.smcommon.model.Post;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private float size;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private Integer postId;
    private String owner;
}
