package com.tqkien03.authservice.dto;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean accountLocked;
    private boolean enabled;

    public String fullName() {
        return firstName + " " + lastName;
    }
}
