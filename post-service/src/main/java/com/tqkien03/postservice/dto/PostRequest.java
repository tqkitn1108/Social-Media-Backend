package com.tqkien03.postservice.dto;

import com.tqkien03.postservice.model.enums.PostStatus;
import lombok.Data;

import java.util.List;

@Data
public class PostRequest {
    private Integer id;
    private PostStatus status;
    private String content;
    private List<Integer> mediaIds;
    private String ownerId;
}
