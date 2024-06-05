package com.tqkien03.smcommon.model;

import com.tqkien03.smcommon.model.enums.PostStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private PostStatus status;
    private String content;
    @OneToMany(mappedBy = "post")
    private List<Media> medias;
    @OneToMany(mappedBy = "post")
    private List<React> reacts;
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Comment> comments;
}
