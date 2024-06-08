package com.tqkien03.postservice.messaging;

import com.tqkien03.postservice.exception.ResourceNotFoundException;
import com.tqkien03.postservice.model.Post;
import com.tqkien03.postservice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostConsumer {
    private final PostRepository postRepository;

    @KafkaListener(topics = "react-topic")
    public void consumeReactEvent(ReactEventInfo info,
                                 @Header(KafkaHeaders.ACKNOWLEDGMENT) Acknowledgment acknowledgment) {
        Post post = postRepository.findById(info.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(info.getPostId())));
        post.setReactsCount(post.getReactsCount() + 1);
        postRepository.save(post);

        if (acknowledgment != null) {
            acknowledgment.acknowledge();
        }
    }

    @KafkaListener(topics = "comment-topic")
    public void consumeCommentEvent(ReactEventInfo info,
                                 @Header(KafkaHeaders.ACKNOWLEDGMENT) Acknowledgment acknowledgment) {
        Post post = postRepository.findById(info.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(info.getPostId())));
        post.setCommentsCount(post.getCommentsCount() + 1);
        postRepository.save(post);

        if (acknowledgment != null) {
            acknowledgment.acknowledge();
        }
    }
}
