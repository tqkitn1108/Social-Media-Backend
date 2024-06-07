package com.tqkien03.commentservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class CommentRequest {
    private Integer id;
    private String content;
    private List<Integer> mediaIds;
    private Integer postId;
    private String userId;
}
