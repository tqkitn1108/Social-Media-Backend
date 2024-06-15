package com.tqkien03.commentservice.mapper;

import com.tqkien03.commentservice.client.UserFeignClient;
import com.tqkien03.commentservice.dto.CommentDto;
import com.tqkien03.commentservice.dto.UserSummary;
import com.tqkien03.commentservice.exception.ResourceNotFoundException;
import com.tqkien03.commentservice.model.Comment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CommentMapper {
    private final MediaMapper mediaMapper;
    private final UserFeignClient userFeignClient;

    public Comment commentDtoToComment(CommentDto commentDto, String myId) {
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

    public CommentDto toCommentDto(Comment comment, String myId) {
        return CommentDto
                .builder()
                .id(comment.getId())
                .content(comment.getContent())
                .medias(comment.getMedias().stream().map(mediaMapper::toMediaDto).toList())
                .createdAt(comment.getCreatedAt())
                .lastModifiedAt(comment.getLastModifiedAt())
                .postId(comment.getPostId())
                .user(getUserSummary(comment, myId))
                .build();
    }

    public List<CommentDto> commentsToCommentDtos(List<Comment> comments, String myId) {
        return comments.stream().map(comment -> toCommentDto(comment, myId)).toList();
    }

    public UserSummary getUserSummary(Comment comment, String myId) {
        return userFeignClient.getUserSummary(comment.getUserId(), myId);
    }
}
