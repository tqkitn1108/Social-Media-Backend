package com.tqkien03.smcommon.repository;

import com.tqkien03.smcommon.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    @Query(value = "SELECT followers_id FROM user_followers WHERE followings_id=?1",
            countQuery = "SELECT count(*) FROM user_followers WHERE followings_id=?1",
            nativeQuery = true)
    List<String> getFollowersByUserId(String userId, Pageable pageable);

    @Query(value = "SELECT followings_id FROM user_followers WHERE followers_id=?1",
            countQuery = "SELECT count(*) FROM user_followers WHERE followers_id=?1",
            nativeQuery = true)
    List<String> getFollowingsByUserId(String targetId, Pageable pageable);

    @Query(value = "SELECT friend_id FROM user_friends WHERE user_id=?1",
            countQuery = "SELECT count(*) FROM user_friends WHERE user_id=?1",
            nativeQuery = true)
    List<String> getFriendsByUserId(String targetId, Pageable pageable);
}
