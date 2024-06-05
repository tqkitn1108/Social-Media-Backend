package com.tqkien03.smcommon.dto;

import com.tqkien03.smcommon.model.UserProfile;
import com.tqkien03.smcommon.model.enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileDto {
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private Gender gender;
    private String country;

    public UserProfile toProfile() {
        return UserProfile
                .builder()
                .dateOfBirth(dateOfBirth)
                .phoneNumber(phoneNumber)
                .gender(gender)
                .country(country)
                .build();
    }
}
