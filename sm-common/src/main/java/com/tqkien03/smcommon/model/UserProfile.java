package com.tqkien03.smcommon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tqkien03.smcommon.model.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_profile")
public class UserProfile {
    @Id
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private Gender gender;
    private String country;
    @OneToOne
    @JoinColumn(name = "user_id")
    @Column(unique = true)
    @JsonIgnore
    private User user;
}
