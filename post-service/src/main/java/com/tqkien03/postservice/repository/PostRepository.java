package com.tqkien03.postservice.repository;

import com.tqkien03.postservice.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByOwnerIdOrderByCreatedAtDesc(String userId);
}
