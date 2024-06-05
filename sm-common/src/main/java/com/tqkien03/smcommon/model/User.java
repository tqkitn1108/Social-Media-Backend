package com.tqkien03.smcommon.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class User {
    @Id
    private String id;
    private String avatarUrl;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserProfile userProfile;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private Set<User> friends;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "friend_pendings",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "pending_id")
    )
    private Set<User> friendPendings;
    @ManyToMany(mappedBy = "friendPendings", fetch = FetchType.LAZY)
    private Set<User> friendRequests;
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<User> followers;
    @ManyToMany(mappedBy = "followers", fetch = FetchType.LAZY)
    private Set<User> followings;
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Post> posts;
    @Embedded
    private Activity activity;
}
