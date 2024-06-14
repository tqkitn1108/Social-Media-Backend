package com.tqkien03.feedservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Post {
    @Id
    private Integer id;
    @ManyToMany
    private Set<Feed> feeds;
}
