package com.tqkien03.commentservice.repository;

import com.tqkien03.commentservice.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
