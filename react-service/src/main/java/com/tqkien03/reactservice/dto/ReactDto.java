package com.tqkien03.reactservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReactDto {
    private Integer id;
    private Integer postId;
    private String type;
    private LocalDateTime createdAt;
    private UserSummary user;
}
