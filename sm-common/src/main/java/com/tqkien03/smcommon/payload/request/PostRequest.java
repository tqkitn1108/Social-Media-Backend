package com.tqkien03.smcommon.payload.request;

import com.tqkien03.smcommon.model.enums.PostStatus;
import lombok.Data;

import java.util.List;

@Data
public class PostRequest {
    private PostStatus status;
    private String content;
    private List<Integer> mediaIds;
}
