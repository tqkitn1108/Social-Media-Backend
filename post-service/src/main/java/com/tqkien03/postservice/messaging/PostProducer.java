package com.tqkien03.postservice.messaging;

import com.tqkien03.postservice.model.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostProducer {
    private final KafkaTemplate<String, PostEventInfo> kafkaTemplate;

    public void sendPostCreated(Post post) {
        log.info("sending post created event for post id {}", post.getId());
        sendPostChangedEvent(convertTo(post, PostEventType.CREATED));
    }

    public void sendPostUpdated(Post post) {
        log.info("sending post updated event for post {}", post.getId());
        sendPostChangedEvent(convertTo(post, PostEventType.UPDATED));
    }

    public void sendPostDeleted(Post post) {
        log.info("sending post deleted event for post {}", post.getId());
        sendPostChangedEvent(convertTo(post, PostEventType.DELETED));
    }

    private void sendPostChangedEvent(PostEventInfo info) {

        Message<PostEventInfo> message = MessageBuilder
                .withPayload(info)
                .setHeader(KafkaHeaders.TOPIC, "post-topic")
                .setHeader(KafkaHeaders.KEY, String.valueOf(info.getOwnerId()))
                .build();

        kafkaTemplate.send(message);

        log.info("post event {} sent to topic {} for post {} and user {}",
                message.getPayload().getEventType().name(),
                "postChanged",
                message.getPayload().getPostId(),
                message.getPayload().getOwnerId());
    }

    private PostEventInfo convertTo(Post post, PostEventType eventType) {
        return PostEventInfo
                .builder()
                .eventType(eventType)
                .postId(post.getId())
                .ownerId(post.getOwnerId())
                .build();
    }
}
