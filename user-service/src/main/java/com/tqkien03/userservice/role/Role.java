package com.tqkien03.userservice.role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tqkien03.userservice.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private ERole name;
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<User> users;
    public Role(ERole name) {
        this.name = name;
    }
}
