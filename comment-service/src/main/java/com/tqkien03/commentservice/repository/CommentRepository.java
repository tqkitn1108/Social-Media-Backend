package com.tqkien03.commentservice.repository;

import com.tqkien03.commentservice.model.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByPostIdOrderByCreatedAt(Integer postId, Pageable pageable);
}
