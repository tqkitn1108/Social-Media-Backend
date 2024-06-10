package com.tqkien03.commentservice.mapper;

import com.tqkien03.commentservice.client.UserFeignClient;
import com.tqkien03.commentservice.dto.CommentDto;
import com.tqkien03.commentservice.dto.UserSummary;
import com.tqkien03.commentservice.exception.ResourceNotFoundException;
import com.tqkien03.commentservice.model.Comment;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CommentMapper {
    private final MediaMapper mediaMapper;
    private final UserFeignClient userFeignClient;

    public Comment commentDtoToComment(CommentDto commentDto, Authentication authentication) {
        return Comment
                .builder()
                .id(commentDto.getId())
                .content(commentDto.getContent())
                .createdAt(commentDto.getCreatedAt())
                .lastModifiedAt(commentDto.getLastModifiedAt())
                .medias(commentDto.getMedias().stream().map(mediaMapper::mediaDtoToMedia).toList())
                .postId(commentDto.getPostId())
                .userId(commentDto.getUser().getId())
                .build();

    }

    public CommentDto toCommentDto(Comment comment, Authentication authentication) {
        return CommentDto
                .builder()
                .id(comment.getId())
                .content(comment.getContent())
                .medias(comment.getMedias().stream().map(mediaMapper::toMediaDto).toList())
                .createdAt(comment.getCreatedAt())
                .lastModifiedAt(comment.getLastModifiedAt())
                .postId(comment.getPostId())
                .user(getUserSummary(comment, authentication))
                .build();
    }

    public List<CommentDto> commentsToCommentDtos(List<Comment> comments, Authentication authentication) {
        return comments.stream().map(comment -> toCommentDto(comment, authentication)).toList();
    }

    public UserSummary getUserSummary(Comment comment, Authentication authentication) {
        return userFeignClient.getUserSummary(comment.getUserId(), authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User" + authentication.getName() + "not found"));
    }
}
