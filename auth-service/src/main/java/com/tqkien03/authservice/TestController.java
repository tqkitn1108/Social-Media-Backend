package com.tqkien03.authservice;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {
    @GetMapping("/{book-id}")
    public ResponseEntity<String> test(@PathVariable("book-id") String bookId, Authentication connectedUser) {
        System.out.println(connectedUser.getName());
        System.out.println(connectedUser.getDetails().toString());
        return ResponseEntity.ok(connectedUser.getName());
    }
}
