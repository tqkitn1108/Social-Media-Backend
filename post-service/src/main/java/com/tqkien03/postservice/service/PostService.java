package com.tqkien03.postservice.service;

import com.tqkien03.postservice.exception.NotAllowedException;
import com.tqkien03.postservice.exception.ResourceNotFoundException;
import com.tqkien03.postservice.mapper.PostMapper;
import com.tqkien03.smcommon.dto.PostDto;
import com.tqkien03.smcommon.model.Media;
import com.tqkien03.smcommon.model.Post;
import com.tqkien03.smcommon.model.User;
import com.tqkien03.smcommon.payload.request.PostRequest;
import com.tqkien03.smcommon.repository.MediaRepository;
import com.tqkien03.smcommon.repository.PostRepository;
import com.tqkien03.smcommon.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final MediaRepository mediaRepository;
    private final PostMapper postMapper;

    public PostDto createPost(PostRequest request, String ownerId) {
        User owner = userRepository.findById(ownerId).orElseThrow(() -> new ResourceNotFoundException(ownerId));
        List<Media> medias = mediaRepository.findAllById(request.getMediaIds());
        Post post = Post.builder()
                .status(request.getStatus())
                .owner(owner)
                .content(request.getContent())
                .medias(medias)
                .build();
        postRepository.save(post);

        medias.forEach(media -> media.setPost(post));
        mediaRepository.saveAll(medias);

        owner.getPosts().add(post);
        owner.getActivity().setPostsCount(owner.getPosts().size());
        userRepository.save(owner);
        return postMapper.toPostDto(post);
    }

    public PostDto updatePost(PostRequest request, Integer postId, String userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(postId)));
        if (!post.getOwner().getId().equals(userId)) {
            throw new NotAllowedException(userId, String.valueOf(postId), "UPDATE");
        }
        post.setContent(request.getContent());
        List<Media> oldMedias = post.getMedias();
        oldMedias.forEach(oldMedia -> oldMedia.setPost(null));
        mediaRepository.saveAll(oldMedias);
        List<Media> medias = mediaRepository.findAllById(request.getMediaIds());
        post.setMedias(medias);
        medias.forEach(media -> media.setPost(post));
        mediaRepository.saveAll(medias);
        postRepository.save(post);
        return postMapper.toPostDto(post);
    }

    public PostDto getPost(Integer postId, String userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(postId)));
        return postMapper.toPostDto(post);
    }

    public void deletePost(Integer postId, String userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(postId)));
        if (!post.getOwner().getId().equals(userId)) {
            throw new NotAllowedException(userId, String.valueOf(postId), "DELETE");
        }
        postRepository.delete(post);
    }

    public List<PostDto> getPostsByUserId(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(userId));
        List<Post> posts = postRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return postMapper.postsToPostDtos(posts);
    }
}
