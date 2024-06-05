package com.tqkien03.smcommon.repository;

import com.tqkien03.smcommon.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
