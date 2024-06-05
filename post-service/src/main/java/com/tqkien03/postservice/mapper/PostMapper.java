package com.tqkien03.postservice.mapper;

import com.tqkien03.smcommon.dto.PostDto;
import com.tqkien03.smcommon.model.Post;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PostMapper {
    private final MediaMapper mediaMapper;
    public PostDto toPostDto(Post post) {
        return PostDto
                .builder()
                .id(post.getId())
                .owner(post.getOwner())
                .content(post.getContent())
                .medias(post.getMedias().stream().map(mediaMapper::toMediaDto).toList())
                .createdAt(post.getCreatedAt())
                .lastModifiedAt(post.getLastModifiedAt())
                .reactsCount(post.getReacts().size())
                .commentsCount(post.getComments().size())
                .build();

    }
}
