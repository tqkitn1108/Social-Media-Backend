package com.tqkien03.postservice.mapper;

import com.tqkien03.postservice.client.UserFeignClient;
import com.tqkien03.postservice.dto.PostDto;
import com.tqkien03.postservice.dto.UserSummary;
import com.tqkien03.postservice.exception.ResourceNotFoundException;
import com.tqkien03.postservice.model.Post;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PostMapper {
    private final UserFeignClient userFeignClient;

    public PostDto toPostDto(Post post, String myId) {
        return PostDto
                .builder()
                .id(post.getId())
                .content(post.getContent())
                .medias(post.getMedias())
                .createdAt(post.getCreatedAt())
                .lastModifiedAt(post.getLastModifiedAt())
                .reactsCount(post.getReactsCount())
                .commentsCount(getCommentsCount(post))
                .user(getUserSummary(post, myId))
                .build();
    }

    public List<PostDto> postsToPostDtos(List<Post> posts, String myId) {
        return posts.stream().map(post -> toPostDto(post, myId)).collect(Collectors.toList());
    }

    public UserSummary getUserSummary(Post post, String myId) {
            return userFeignClient.getUserSummary(post.getOwnerId(), myId)
                .orElseThrow(() -> new ResourceNotFoundException(post.getOwnerId()));
    }

    private int getCommentsCount(Post post) {
        return 0;
    }

}
