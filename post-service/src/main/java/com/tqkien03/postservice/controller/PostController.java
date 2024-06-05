package com.tqkien03.postservice.controller;

import com.tqkien03.postservice.service.PostService;
import com.tqkien03.smcommon.dto.PostDto;
import com.tqkien03.smcommon.payload.request.PostRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostRequest postRequest, Authentication authentication) {
        PostDto postDto = postService.createPost(postRequest, authentication.getName());
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(postDto.getId()).toUri();

        return ResponseEntity.created(uri).body(postDto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@RequestBody PostRequest postRequest,
                                        @PathVariable Integer id,
                                        Authentication authentication){
        PostDto postDto = postService.updatePost(postRequest, id, authentication.getName());
        return ResponseEntity.ok(postDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Integer id, Authentication authentication){
        PostDto postDto = postService.getPost(id, authentication.getName());
        return ResponseEntity.ok(postDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Integer id, Authentication authentication){
        postService.deletePost(id, authentication.getName());
        return ResponseEntity.ok("Delete post successfully");
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyPosts(Authentication authentication) {
        List<PostDto> postDtos = postService.getPostsByUserId(authentication.getName());
        if (postDtos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(postDtos);
    }

}
