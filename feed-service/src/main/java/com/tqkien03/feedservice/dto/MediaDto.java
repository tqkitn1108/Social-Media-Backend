package com.tqkien03.feedservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MediaDto {
    private Integer id;
    private String mediaName;
    private String mediaType;
    private String url;
    private float height;
    private float width;
    private float size;
}
