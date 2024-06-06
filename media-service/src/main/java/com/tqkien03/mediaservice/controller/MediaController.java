package com.tqkien03.mediaservice.controller;

import com.tqkien03.mediaservice.service.MediaService;
import com.tqkien03.smcommon.dto.MediaDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/medias")
@AllArgsConstructor
public class MediaController {
    private final MediaService mediaService;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam MultipartFile file, Authentication authentication) {
        MediaDto mediaDto = mediaService.uploadToCloudinary(file, authentication.getName());
        return mediaDto != null ?
                ResponseEntity.ok(mediaDto) :
                ResponseEntity.badRequest().body("Can not upload file");
    }
}
