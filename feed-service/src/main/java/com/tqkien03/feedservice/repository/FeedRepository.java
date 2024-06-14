package com.tqkien03.feedservice.repository;

import com.tqkien03.feedservice.model.Feed;
import org.springframework.data.jpa.repository.JpaRepository;



public interface FeedRepository extends JpaRepository<Feed, String> {
    Feed findByUserId(String userId);
}
