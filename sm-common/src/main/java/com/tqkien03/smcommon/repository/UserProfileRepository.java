package com.tqkien03.smcommon.repository;

import com.tqkien03.smcommon.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProfileRepository extends JpaRepository<UserProfile, String>{
    List<UserProfile> findByFullNameContainingIgnoreCase(String key);
}
