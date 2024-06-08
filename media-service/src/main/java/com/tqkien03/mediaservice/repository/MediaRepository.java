package com.tqkien03.mediaservice.repository;

import com.tqkien03.mediaservice.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaRepository extends JpaRepository<Media, Integer> {
    List<Media> findByOwnerId(String ownerId);
}
