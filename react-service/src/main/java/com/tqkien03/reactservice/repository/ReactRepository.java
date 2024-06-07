package com.tqkien03.reactservice.repository;

import com.tqkien03.reactservice.model.React;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReactRepository extends JpaRepository<React, Integer> {
    Optional<React> findByPostIdAndUserId(Integer postId, String userId);
    List<React> findByPostId(Integer postId, Pageable pageable);
}
