package com.tqkien03.authservice.service;

import com.tqkien03.authservice.client.UserServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//@Service
//@RequiredArgsConstructor
public class UserDetailsServiceImpl { // implements UserDetailsService {
    /*private final UserServiceClient userServiceClient;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userServiceClient.getUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }*/
}
