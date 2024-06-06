package com.tqkien03.postservice.repository;

import com.tqkien03.postservice.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, Integer> {
}
