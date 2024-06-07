package com.tqkien03.feedservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document
public class Feed {
    @Id
    private String id;
    private String userId;
    private String fullName;
    private Set<Integer> postIds;
}
