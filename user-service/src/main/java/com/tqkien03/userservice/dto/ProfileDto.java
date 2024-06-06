package com.tqkien03.userservice.dto;

import com.tqkien03.userservice.model.Profile;
import com.tqkien03.userservice.model.enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileDto {
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private Gender gender;
    private String country;

    public Profile toProfile() {
        return Profile
                .builder()
                .dateOfBirth(dateOfBirth)
                .phoneNumber(phoneNumber)
                .gender(gender)
                .country(country)
                .build();
    }
}
