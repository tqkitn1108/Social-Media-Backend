package com.tqkien03.mediaservice.media;

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
                .height(media.getHeight())
                .width(media.getWidth())
                .size(media.getSize())
                .createdAt(media.getCreatedAt())
                .lastModifiedAt(media.getLastModifiedAt())
                .postId(media.getPost().getId())
                .ownerId(media.getOwner().getId())
                .build();
    }
}
