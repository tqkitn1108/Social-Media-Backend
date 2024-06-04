package com.tqkien03.authservice.dto;

import com.tqkien03.authservice.ERole;
import lombok.Data;

@Data
public class Role {
    private Integer id;
    private ERole name;
}
