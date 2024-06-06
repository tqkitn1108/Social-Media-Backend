package com.tqkien03.commentservice.repository;

import com.tqkien03.commentservice.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, Integer> {
}
