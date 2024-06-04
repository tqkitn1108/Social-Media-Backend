package com.tqkien03.authservice;

import com.tqkien03.authservice.dto.request.AuthenticationRequest;
import com.tqkien03.authservice.dto.request.RegistrationRequest;
import com.tqkien03.authservice.dto.response.AuthenticationResponse;
import com.tqkien03.authservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@RestController
//@RequestMapping("/api/v1/auth")
//@RequiredArgsConstructor
public class AuthController {
    /*private final AuthService service;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> register(
            @RequestBody @Valid RegistrationRequest request) {
        service.register(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/activate-account")
    public void confirm(@RequestParam String token){
        service.activateAccount(token);
    }*/
}
