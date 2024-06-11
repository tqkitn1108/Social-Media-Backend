package com.tqkien03.commentservice.controller;

import com.tqkien03.commentservice.dto.CommentDto;
import com.tqkien03.commentservice.dto.CommentRequest;
import com.tqkien03.commentservice.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<?> addComment(@RequestBody CommentRequest commentRequest,
                                        Authentication authentication) {
        CommentDto commentDto = commentService.addComment(commentRequest, authentication);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/comments/{postId}")
                .buildAndExpand(commentDto.getId()).toUri();
        return ResponseEntity.created(uri).body(commentDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@RequestBody CommentRequest commentRequest,
                                           @PathVariable Integer id,
                                           Authentication authentication) {
        CommentDto commentDto = commentService.updateComment(commentRequest, id, authentication);
        return ResponseEntity.ok(commentDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getComment(@PathVariable Integer id, Authentication authentication) {
        CommentDto commentDto = commentService.getComment(id, authentication);
        return ResponseEntity.ok(commentDto);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getCommentsByPost(@PathVariable Integer postId, Authentication authentication,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        List<CommentDto> commentDtos = commentService.getCommentsByPostId(postId, authentication, page, size);
        return commentDtos.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(commentDtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok("Delete successfully");
    }
}
