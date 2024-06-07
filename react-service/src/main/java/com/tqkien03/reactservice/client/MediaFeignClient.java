package com.tqkien03.reactservice.client;

import com.tqkien03.commentservice.dto.MediaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "media-service", url = "${application.config.media-url}")
public interface MediaFeignClient {
    @GetMapping("/{id}")
    MediaDto findMediaById(@PathVariable Integer id);
    @GetMapping
    List<MediaDto> findMediasByListId(@RequestParam List<Integer> mediaIds);
}