package com.tqkien03.postservice.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "comment-service", url = "${application.config.comment-url}")
public interface CommentFeignClient {
}
