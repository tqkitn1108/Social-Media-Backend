package com.tqkien03.reactservice.controller;

import com.tqkien03.reactservice.dto.ReactDto;
import com.tqkien03.reactservice.dto.ReactRequest;
import com.tqkien03.reactservice.service.ReactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reacts")
@RequiredArgsConstructor
public class ReactController {
    private final ReactService reactService;

    @PostMapping
    public ResponseEntity<?> addReact(@RequestBody ReactRequest request, Authentication authentication) {
        return reactService.addReact(request, authentication) ?
                ResponseEntity.ok("Success") :
                ResponseEntity.badRequest().body("Failed");
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getReactsByPost(@PathVariable Integer postId, Authentication authentication,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {
        List<ReactDto> reactDtos = reactService.getReacts(postId, authentication, page, size);
        return reactDtos.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(reactDtos);
    }
}
