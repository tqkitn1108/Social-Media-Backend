package com.tqkien03.postservice.mapper;

import com.tqkien03.postservice.dto.MediaDto;
import com.tqkien03.postservice.model.Media;
import org.springframework.stereotype.Component;

@Component
public class MediaMapper {

    public MediaDto toMediaDto(Media media) {
        return MediaDto
                .builder()
                .id(media.getId())
                .mediaName(media.getMediaName())
                .mediaType(media.getMediaType())
                .url(media.getUrl())
                .height(media.getHeight())
                .width(media.getWidth())
                .size(media.getSize())
                .createdAt(media.getCreatedAt())
                .lastModifiedAt(media.getLastModifiedAt())
                .postId(media.getPost().getId())
                .ownerId(media.getOwnerId())
                .build();
    }

    public Media mediaDtoToMedia(MediaDto mediaDto) {
        return Media
                .builder()
                .id(mediaDto.getId())
                .mediaName(mediaDto.getMediaName())
                .mediaType(mediaDto.getMediaType())
                .url(mediaDto.getUrl())
                .height(mediaDto.getHeight())
                .width(mediaDto.getWidth())
                .size(mediaDto.getSize())
                .createdAt(mediaDto.getCreatedAt())
                .lastModifiedAt(mediaDto.getLastModifiedAt())
                .ownerId(mediaDto.getOwnerId())
                .build();
    }

}
