package com.tqkien03.smcommon.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post extends BaseEntity {
    private Integer likes;
    private PostStatus status;
    private String content;
    @OneToMany(mappedBy = "post")
    private List<Media> medias;
    @OneToMany(mappedBy = "post")
    private List<Comment> comments;
}
