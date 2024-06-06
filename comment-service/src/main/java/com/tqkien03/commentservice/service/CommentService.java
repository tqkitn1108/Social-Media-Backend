package com.tqkien03.commentservice.service;

import com.tqkien03.commentservice.client.PostFeignClient;
import com.tqkien03.commentservice.dto.CommentDto;
import com.tqkien03.commentservice.dto.PostDto;
import com.tqkien03.commentservice.dto.UserSummary;
import com.tqkien03.commentservice.exception.NotAllowedException;
import com.tqkien03.commentservice.exception.ResourceNotFoundException;
import com.tqkien03.commentservice.mapper.CommentMapper;
import com.tqkien03.commentservice.model.Comment;
import com.tqkien03.commentservice.model.Media;
import com.tqkien03.commentservice.repository.CommentRepository;
import com.tqkien03.commentservice.repository.MediaRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MediaRepository mediaRepository;
    private final CommentMapper commentMapper;
    private final PostFeignClient postFeignClient;

    public CommentDto addComment(CommentDto commentDto, Authentication authentication) {
        String userId = authentication.getName();
        UserSummary userSummary = commentDto.getUser();
        if (!userSummary.getId().equals(userId)) {
            throw new NotAllowedException(userId, commentDto.getContent(), "comment");
        }
        int postId = commentDto.getPostId();
        PostDto postDto = postFeignClient.getPost(postId, authentication)
                .orElseThrow(() -> new ResourceNotFoundException("Post" + postId + "not found"));
        Comment comment = commentMapper.commentDtoToComment(commentDto, authentication);
        commentRepository.save(comment);
        List<Media> medias = mediaRepository.findAllById(mediaIds);
        for (Media media : medias) {
            media.setComment(comment);
        }
        mediaRepository.saveAll(medias);
        comment.setMedias(medias);

        commentRepository.save(comment);
        commentEventSender.sendCommentCreated(comment);
        return commentMapper.commentToCommentDto(comment, RedisUtil.getInstance().getValue(userId));
    }

    public CommentDto getComment(Integer commentId, String userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(commentId)));
        return commentMapper.toCommentDto(comment);
    }

    public List<CommentDto> getCommentsByPost(Integer postId, String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Comment> comments = commentRepository.findAllByPostId(postId, pageable);
        return commentMapper.commentsToCommentDtos(comments);
    }

    public void deleteComment(Integer commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(commentId)));
        commentRepository.delete(comment);
    }
}
