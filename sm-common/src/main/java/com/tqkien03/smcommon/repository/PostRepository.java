package com.tqkien03.smcommon.repository;

import com.tqkien03.smcommon.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUserIdOrderByCreatedAtDesc(String userId);
}
