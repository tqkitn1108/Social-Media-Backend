package com.tqkien03.feedservice.messaging;

import com.tqkien03.feedservice.service.FeedGeneratorService;
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
public class FeedConsumer {
    private final FeedGeneratorService feedGeneratorService;

    @KafkaListener(topics = "post-topic")
    public void consumePostEvent(PostEventInfo info,
                                 @Header(KafkaHeaders.ACKNOWLEDGMENT) Acknowledgment acknowledgment) {

        PostEventType eventType = info.getEventType();

        log.info("received info to process post {} for user {} eventType {}",
                info.getPostId(),
                info.getOwnerId(),
                eventType.name());

        switch (eventType) {
            case CREATED -> feedGeneratorService.addToFeed(info);
            case DELETED -> feedGeneratorService.deleteFromFeed(info);
        }

        if (acknowledgment != null) {
            acknowledgment.acknowledge();
        }
    }
}
