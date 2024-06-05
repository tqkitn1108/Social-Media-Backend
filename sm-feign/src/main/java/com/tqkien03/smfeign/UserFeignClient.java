package com.tqkien03.smfeign;

import com.tqkien03.smcommon.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "user-service", url = "${application.config.users-url}")
public interface UserFeignClient {
    @GetMapping("/get-user-by-email/{email}")
    Optional<User> getUserByEmail(@PathVariable String email);
}
