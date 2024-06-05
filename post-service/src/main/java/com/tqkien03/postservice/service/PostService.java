package com.tqkien03.postservice.service;

import com.tqkien03.smcommon.dto.PostDto;
import com.tqkien03.smcommon.payload.request.PostRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {
    public PostDto createPost(PostRequest request, String userId) {
        return null;
    }
}
