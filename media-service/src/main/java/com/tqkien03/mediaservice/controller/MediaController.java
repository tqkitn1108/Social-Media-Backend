package com.tqkien03.mediaservice.controller;

import com.tqkien03.mediaservice.model.Media;
import com.tqkien03.mediaservice.service.MediaService;
import com.tqkien03.mediaservice.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/medias")
@RequiredArgsConstructor
public class MediaController {
    private final UploadService uploadService;
    private final MediaService mediaService;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam MultipartFile file,
                                    Authentication authentication) {
        Media media = uploadService.uploadToCloudinary(file, authentication.getName());
        return media != null ?
                ResponseEntity.ok(media) :
                ResponseEntity.badRequest().body("Can not upload file");
    }

    @PostMapping("/multi-upload")
    public ResponseEntity<?> multiUpload(@RequestParam("files") List<MultipartFile> files, Authentication authentication) {
        List<Media> medias = new ArrayList<>();
        for (MultipartFile file : files) {
            Media media = uploadService.uploadToCloudinary(file, authentication.getName());
            if (media != null) {
                medias.add(media);
            } else {
                return ResponseEntity.badRequest().body("Can not upload file");
            }
        }
        return ResponseEntity.ok(medias);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> findMediaById(@PathVariable Integer id) {
        return ResponseEntity.ok(mediaService.findMediaById(id));
    }

    @GetMapping
    public ResponseEntity<?> findMediasByListId(@RequestParam List<Integer> mediaIds) {
        return ResponseEntity.ok(mediaService.findMediasByListId(mediaIds));
    }
}
