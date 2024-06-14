package com.tqkien03.feedservice.repository;

import com.tqkien03.feedservice.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
