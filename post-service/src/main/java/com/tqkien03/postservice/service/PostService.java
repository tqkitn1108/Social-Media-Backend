package com.tqkien03.postservice.service;

import com.tqkien03.postservice.client.MediaFeignClient;
import com.tqkien03.postservice.config.KeyCloakJwtAuthenticationConverter;
import com.tqkien03.postservice.dto.MediaDto;
import com.tqkien03.postservice.dto.PostDto;
import com.tqkien03.postservice.dto.PostRequest;
import com.tqkien03.postservice.exception.NotAllowedException;
import com.tqkien03.postservice.exception.ResourceNotFoundException;
import com.tqkien03.postservice.mapper.MediaMapper;
import com.tqkien03.postservice.mapper.PostMapper;
import com.tqkien03.postservice.messaging.PostProducer;
import com.tqkien03.postservice.model.Media;
import com.tqkien03.postservice.repository.MediaRepository;
import com.tqkien03.postservice.repository.PostRepository;
import com.tqkien03.postservice.model.Post;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final MediaRepository mediaRepository;
    private final MediaFeignClient mediaFeignClient;
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final MediaMapper mediaMapper;
    private final PostProducer postProducer;

    public PostDto createPost(PostRequest request, Authentication authentication) {
        String ownerId = authentication.getName();
        if (!request.getOwnerId().equals(ownerId)) throw new NotAllowedException(ownerId, "post", "create");
        List<MediaDto> mediaDtos = mediaFeignClient.findMediasByListId(request.getMediaIds());
        List<Media> medias = mediaDtos.stream().map(mediaMapper::mediaDtoToMedia).toList();
        Post post = Post.builder()
                .status(request.getStatus())
                .content(request.getContent())
                .ownerId(ownerId)
                .medias(medias)
                .build();
        postRepository.save(post);

        medias.forEach(media -> media.setPost(post));
        mediaRepository.saveAll(medias);

        postProducer.sendPostCreated(post);
        return postMapper.toPostDto(post, ownerId);
    }

    public PostDto updatePost(PostRequest request, Integer postId, Authentication authentication) {
        String ownerId = authentication.getName();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(postId)));
        if (!post.getOwnerId().equals(ownerId)) {
            throw new NotAllowedException(ownerId, String.valueOf(postId), "UPDATE");
        }
        post.setContent(request.getContent());
        List<Media> oldMedias = post.getMedias();
        oldMedias.forEach(oldMedia -> oldMedia.setPost(null));
        mediaRepository.saveAll(oldMedias);
        List<MediaDto> mediaDtos = mediaFeignClient.findMediasByListId(request.getMediaIds());
        List<Media> medias = mediaDtos.stream().map(mediaMapper::mediaDtoToMedia).toList();
        post.setMedias(medias);
        medias.forEach(media -> media.setPost(post));
        mediaRepository.saveAll(medias);
        postRepository.save(post);

        postProducer.sendPostUpdated(post);
        return postMapper.toPostDto(post, ownerId);
    }

    public PostDto getPost(Integer postId, Authentication authentication) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(postId)));
        return postMapper.toPostDto(post, authentication.getName());
    }

    public void deletePost(Integer postId, Authentication authentication) {
        String userId = authentication.getName();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(postId)));
        if (!post.getOwnerId().equals(userId)) {
            throw new NotAllowedException(userId, String.valueOf(postId), "DELETE");
        }
        postRepository.delete(post);
    }

    public List<PostDto> getPostsByOwnerId(String ownerId, Authentication authentication) {
        String userId = authentication.getName();
        List<Post> posts = postRepository.findByOwnerIdOrderByCreatedAtDesc(ownerId);
        return postMapper.postsToPostDtos(posts, userId);
    }

    public boolean checkPostExist(Integer id) {
        return postRepository.existsById(id);
    }
}
