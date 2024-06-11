package com.tqkien03.commentservice.mapper;

import com.tqkien03.commentservice.dto.MediaDto;
import com.tqkien03.commentservice.model.Media;
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
                .commentId(media.getComment().getId())
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
                .ownerId(mediaDto.getOwnerId())
                .build();
    }
}
