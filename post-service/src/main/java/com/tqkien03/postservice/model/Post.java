package com.tqkien03.postservice.model;

import com.tqkien03.postservice.model.enums.PostStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Post extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private PostStatus status;
    private String content;
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Media> medias;
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<React> reacts;
    private String ownerId;
}
