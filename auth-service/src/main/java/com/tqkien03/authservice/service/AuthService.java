package com.tqkien03.authservice.service;

import com.tqkien03.authservice.client.UserServiceClient;
import com.tqkien03.authservice.dto.User;
import com.tqkien03.authservice.dto.request.AuthenticationRequest;
import com.tqkien03.authservice.dto.request.RegistrationRequest;
import com.tqkien03.authservice.dto.response.AuthenticationResponse;
import com.tqkien03.authservice.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;


//@Service
//@RequiredArgsConstructor
public class AuthService {
    /*private final UserServiceClient userServiceClient;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public void register(RegistrationRequest request) {
        userServiceClient.saveUser(request);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );

        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());
        claims.put("fullName", user.fullName());

        String jwtToken = jwtService.generateToken(claims, (User) auth.getPrincipal());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Transactional
    public void activateAccount(String token) {
        userServiceClient.confirm(token);
    }*/
}
