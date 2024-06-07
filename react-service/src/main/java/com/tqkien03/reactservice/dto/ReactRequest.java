package com.tqkien03.reactservice.dto;

import lombok.Data;


@Data
public class ReactRequest {
    private Integer id;
    private Integer postId;
    private String type;
    private String userId;
}
