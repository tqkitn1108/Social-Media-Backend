package com.tqkien03.userservice.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    public Optional<User> getUserByEmail(String email) {
        return repository.findByEmail(email);
    }

}
