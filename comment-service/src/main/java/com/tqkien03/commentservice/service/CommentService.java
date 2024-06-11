package com.tqkien03.commentservice.service;

import com.tqkien03.commentservice.client.MediaFeignClient;
import com.tqkien03.commentservice.client.PostFeignClient;
import com.tqkien03.commentservice.dto.*;
import com.tqkien03.commentservice.exception.NotAllowedException;
import com.tqkien03.commentservice.exception.ResourceNotFoundException;
import com.tqkien03.commentservice.mapper.CommentMapper;
import com.tqkien03.commentservice.mapper.MediaMapper;
import com.tqkien03.commentservice.messaging.CommentProducer;
import com.tqkien03.commentservice.model.Comment;
import com.tqkien03.commentservice.model.Media;
import com.tqkien03.commentservice.repository.CommentRepository;
import com.tqkien03.commentservice.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MediaRepository mediaRepository;
    private final CommentMapper commentMapper;
    private final MediaMapper mediaMapper;
    private final PostFeignClient postFeignClient;
    private final MediaFeignClient mediaFeignClient;
    private final CommentProducer commentProducer;

    public CommentDto addComment(CommentRequest request, Authentication authentication) {
        String userId = authentication.getName();
        if (!request.getUserId().equals(userId)) {
            throw new NotAllowedException(userId, request.getContent(), "comment");
        }
        int postId = request.getPostId();
        if(!postFeignClient.checkPostExist(postId))
                throw new ResourceNotFoundException("Post" + postId + "not found");
        List<MediaDto> mediaDtos = mediaFeignClient.findMediasByListId(request.getMediaIds());
        List<Media> medias = mediaDtos.stream().map(mediaMapper::mediaDtoToMedia).toList();
        Comment comment = Comment
                .builder()
                .content(request.getContent())
                .medias(medias)
                .postId(postId)
                .userId(userId)
                .build();
        commentRepository.save(comment);

        medias.forEach(media -> media.setComment(comment));
        mediaRepository.saveAll(medias);

        commentProducer.sendCommentCreated(comment);
        return commentMapper.toCommentDto(comment, userId);
    }

    public CommentDto getComment(Integer commentId, Authentication authentication) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(commentId)));
        return commentMapper.toCommentDto(comment, authentication.getName());
    }

    public CommentDto updateComment(CommentRequest request, Integer commentId, Authentication authentication) {
        String userId = authentication.getName();
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(commentId)));
        if (!comment.getUserId().equals(userId)) {
            throw new NotAllowedException(userId, String.valueOf(commentId), "UPDATE");
        }
        comment.setContent(request.getContent());
        List<Media> oldMedias = comment.getMedias();
        oldMedias.forEach(oldMedia -> oldMedia.setComment(null));
        mediaRepository.saveAll(oldMedias);
        List<MediaDto> mediaDtos = mediaFeignClient.findMediasByListId(request.getMediaIds());
        List<Media> medias = mediaDtos.stream().map(mediaMapper::mediaDtoToMedia).toList();
        comment.setMedias(medias);
        medias.forEach(media -> media.setComment(comment));
        mediaRepository.saveAll(medias);
        commentRepository.save(comment);
        return commentMapper.toCommentDto(comment, userId);
    }

    public List<CommentDto> getCommentsByPostId(Integer postId, Authentication authentication, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Comment> comments = commentRepository.findAllByPostIdOrderByCreatedAt(postId, pageable);
        return commentMapper.commentsToCommentDtos(comments, authentication.getName());
    }

    public void deleteComment(Integer commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(commentId)));
        commentRepository.delete(comment);
        commentProducer.sendCommentDeleted(comment);
    }
}
