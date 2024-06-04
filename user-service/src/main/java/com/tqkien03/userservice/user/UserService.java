package com.tqkien03.userservice.user;

import com.tqkien03.userservice.dto.request.RegistrationRequest;
import com.tqkien03.userservice.email.EmailService;
import com.tqkien03.userservice.email.EmailTemplateName;
import com.tqkien03.userservice.role.ERole;
import com.tqkien03.userservice.role.Role;
import com.tqkien03.userservice.role.RoleRepository;
import com.tqkien03.userservice.verificationtoken.VerificationToken;
import com.tqkien03.userservice.verificationtoken.VerificationTokenRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository tokenRepository;
    private final EmailService emailService;
    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;


    public Optional<User> getUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    public User saveUser(RegistrationRequest input) throws MessagingException {
        String email = input.getEmail().toLowerCase();
        if (repository.existsByEmail(email)) {
            throw new IllegalStateException("User with email " + input.getEmail() + " already exists");
        }
        Optional<Role> optionalRole = roleRepository.findByName(ERole.USER);
        if (optionalRole.isEmpty()) throw new IllegalStateException("Role USER was not initialized");

        User user = User.builder()
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .email(email)
                .password(passwordEncoder.encode(input.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(optionalRole.get())).build();
        repository.save(user);
        sendValidationEmail(user);
        return user;
    }

    public void activateAccount(String token) throws MessagingException {
        VerificationToken savedToken = tokenRepository.findByToken(token)
                // todo exception has to be defined
                .orElseThrow(() -> new RuntimeException("Invalid token"));
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation token has expired. A new token has been send to the same email address");
        }

        var user = repository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);
        repository.save(user);

        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);

        emailService.sendEmail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"
        );
    }

    private String generateAndSaveActivationToken(User user) {
        // Generate a token
        String generatedToken = generateActivationCode(6);
        var token = VerificationToken.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);

        return generatedToken;
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }

        return codeBuilder.toString();
    }
}
