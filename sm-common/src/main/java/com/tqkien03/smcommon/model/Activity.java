package com.tqkien03.smcommon.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Activity {
    private int likesCount;
    private int commentsCount;
    private int postsCount;
    private int followersCount;
    private int followingsCount;
    private int friendsCount;
    private int friendsPending;
}
