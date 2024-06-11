package com.tqkien03.commentservice.messaging;

import com.tqkien03.commentservice.model.Comment;
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
public class CommentProducer {
    private final KafkaTemplate<String, CommentEventInfo> kafkaTemplate;

    public void sendCommentCreated(Comment comment) {
        log.info("sending comment created event for post id {}", comment.getPostId());
        sendCommentNotification(convertTo(comment, CommentEventType.CREATED));
    }

    public void sendCommentDeleted(Comment comment) {
        log.info("sending comment deleted event for post id {}", comment.getPostId());
        sendCommentNotification(convertTo(comment, CommentEventType.DELETED));
    }

    private void sendCommentNotification(CommentEventInfo info) {

        Message<CommentEventInfo> message = MessageBuilder
                .withPayload(info)
                .setHeader(KafkaHeaders.TOPIC, "comment-topic")
                .setHeader(KafkaHeaders.KEY, String.valueOf(info.getPostId()))
                .build();

        kafkaTemplate.send(message);
    }

    private CommentEventInfo convertTo(Comment comment, CommentEventType eventType) {
        return CommentEventInfo
                .builder()
                .postId(comment.getPostId())
                .userId(comment.getUserId())
                .createdAt(comment.getCreatedAt())
                .eventType(eventType)
                .build();
    }
}
