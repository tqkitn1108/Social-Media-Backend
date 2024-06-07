package com.tqkien03.reactservice.messaging;

import com.tqkien03.reactservice.model.React;
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
public class ReactProducer {
    private final KafkaTemplate<String, ReactEventInfo> kafkaTemplate;

    public void sendReactCreated(React react) {
        log.info("sending react created event for post id {}", react.getPostId());
        sendReactNotification(convertTo(react, ReactEventType.CREATED));
    }

    private void sendReactNotification(ReactEventInfo info) {

        Message<ReactEventInfo> message = MessageBuilder
                .withPayload(info)
                .setHeader(KafkaHeaders.TOPIC, "react-topic")
                .setHeader(KafkaHeaders.KEY, String.valueOf(info.getPostId()))
                .build();

        kafkaTemplate.send(message);

        log.info("post event {} sent to topic {} for post {} and user {}",
                message.getPayload().getEventType().name(),
                "reactChanged",
                message.getPayload().getPostId(),
                message.getPayload().getUserId());
    }

    private ReactEventInfo convertTo(React react, ReactEventType eventType) {
        return ReactEventInfo
                .builder()
                .eventType(eventType)
                .postId(react.getPostId())
                .userId(react.getUserId())
                .createdAt(react.getCreatedAt())
                .build();
    }
}
