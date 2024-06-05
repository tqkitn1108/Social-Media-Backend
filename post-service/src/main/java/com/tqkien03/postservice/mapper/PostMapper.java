package com.tqkien03.postservice.mapper;

import com.tqkien03.smcommon.dto.PostDto;
import com.tqkien03.smcommon.dto.UserSummary;
import com.tqkien03.smcommon.model.Post;
import com.tqkien03.smfeign.UserFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PostMapper {
    private final MediaMapper mediaMapper;
    private final UserFeignClient userFeignClient;
    public PostDto toPostDto(Post post) {
        return PostDto
                .builder()
                .id(post.getId())
                .content(post.getContent())
                .medias(post.getMedias().stream().map(mediaMapper::toMediaDto).toList())
                .createdAt(post.getCreatedAt())
                .lastModifiedAt(post.getLastModifiedAt())
                .reactsCount(post.getReacts().size())
                .commentsCount(post.getComments().size())
                .user(getUserSummary(post))
                .build();
    }
    public List<PostDto> postsToPostDtos(List<Post> posts) {
        return posts.stream().map(this::toPostDto).collect(Collectors.toList());
    }

    public UserSummary getUserSummary(Post post) {
        return userFeignClient.getUserSummary(post.getOwner().getId());
    }

}
