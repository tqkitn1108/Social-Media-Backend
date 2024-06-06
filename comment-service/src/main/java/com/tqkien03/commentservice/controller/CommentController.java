package com.tqkien03.commentservice.controller;

import com.tqkien03.commentservice.dto.CommentDto;
import com.tqkien03.commentservice.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/comments")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @MessageMapping("/comment.create")
    @SendTo("/topic/comment/{postId}")
    @PostMapping
    public ResponseEntity<?> addComment(@DestinationVariable Integer postId,
                                        @RequestBody CommentDto commentRequest,
                                        Authentication authentication) {
        CommentDto commentDto = commentService.addComment(commentRequest, authentication);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/comments/{postId}")
                .buildAndExpand(commentDto.getId()).toUri();
        return ResponseEntity.created(uri).body(commentDto);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateComment(@RequestBody CommentDto commentDto,
//                                           @PathVariable Integer id,
//                                           Authentication authentication){
//        postService.updatePost(postRequest, id, authentication.getName());
//        return ResponseEntity.ok("Updated the comment successfully");
//    }

    @MessageMapping("/comment.get")
    @SendTo("/topic/comment/{postId}")
    @GetMapping("/{id}")
    public ResponseEntity<?> getComment(@PathVariable Integer id, Authentication authentication) {
        CommentDto commentDto = commentService.getComment(id, authentication.getName());
        return ResponseEntity.ok(commentDto);
    }

    @MessageMapping("/comment.get.all")
    @SendTo("/topic/comment/{postId}")
    @GetMapping("/by-post/{postId}")
    public ResponseEntity<?> getCommentsByPost(@PathVariable Integer postId, Authentication authentication,
                                               @RequestParam(defaultValue = "0" ) int page,
                                               @RequestParam(defaultValue = "10") int size) {
        List<CommentDto> commentDtos = commentService.getCommentsByPost(postId, authentication.getName(), page, size);
        return commentDtos.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(commentDtos);
    }

    @MessageMapping("/comment.delete")
    @SendTo("/topic/comment/{postId}")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok("Delete successfully");
    }
}
