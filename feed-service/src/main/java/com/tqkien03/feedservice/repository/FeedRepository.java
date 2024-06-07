package com.tqkien03.feedservice.repository;

import com.tqkien03.feedservice.model.Feed;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;


public interface FeedRepository extends MongoRepository<Feed, String> {
    Feed findByUserId(String userId);
    @Query("{ 'postIds': ?0 }")
    List<Feed> findByPostId(Integer postId);
}
