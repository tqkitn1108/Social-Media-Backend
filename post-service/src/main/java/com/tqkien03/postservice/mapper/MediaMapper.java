package com.tqkien03.postservice.mapper;

import com.tqkien03.smcommon.dto.MediaDto;
import com.tqkien03.smcommon.model.Media;
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
                .size(media.getSize())
                .createdAt(media.getCreatedAt())
                .lastModifiedAt(media.getLastModifiedAt())
                .postId(media.getPost().getId())
                .owner(media.getOwner())
                .build();
    }
}
