package com.tqkien03.feedservice.messaging;

import com.tqkien03.feedservice.service.FeedGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedConsumer {
    private final FeedGeneratorService feedGeneratorService;

    @KafkaListener(topics = "post-topic")
    public void consumePostEvent(PostEventInfo info) {
        PostEventType eventType = info.getEventType();

        switch (eventType) {
            case CREATED, UPDATED -> feedGeneratorService.addToFeed(info);
            case DELETED -> feedGeneratorService.deleteFromFeed(info);
        }
    }
}
