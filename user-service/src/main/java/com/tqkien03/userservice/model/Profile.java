package com.tqkien03.userservice.model;

import com.tqkien03.userservice.model.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Profile {
    private String fullName;
    private String email;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String country;
}
