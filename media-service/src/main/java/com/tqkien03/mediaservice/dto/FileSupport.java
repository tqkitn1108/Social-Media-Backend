package com.tqkien03.mediaservice.dto;

import lombok.Getter;

@Getter
public enum FileSupport {
    IMAGE("image/jpg, image/jpeg, image/png, image/gif, image/bmp, image/svg+xml, image/tiff, image/vnd.ms-photo, image/webp, image/x-ms-bmp"),

    AUDIO("audio/mp3, audio/mpeg, audio/m4a, audio/mp4, audio/x-m4a, audio/wav, audio/x-wav, audio/x-ms-wma, video/x-ms-asf"),

    VIDEO("video/mp4, video/mov, video/quicktime, video/m4v, video/x-m4v, video/x-ms-wmv"),

    FILE("");

    private String types;

    FileSupport(String types) {
        this.types = types;
    }
    public void setTypes(String types) {
        this.types = types;
    }

    public static String getFileType(String contentType) {
        if (contentType != null) {
            for (FileSupport item : FileSupport.values()) {
                if (item.getTypes().toLowerCase().contains(contentType.toLowerCase())) {
                    return item.name();
                }
            }
        }

        return null;
    }
}
