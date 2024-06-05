package com.tqkien03.smfeign;

import com.tqkien03.smcommon.dto.UserSummary;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "user-service", url = "${application.config.users-url}")
public interface UserFeignClient {
    @GetMapping("/summary/{userId}")
    UserSummary getUserSummary(@PathVariable String userId);
}
