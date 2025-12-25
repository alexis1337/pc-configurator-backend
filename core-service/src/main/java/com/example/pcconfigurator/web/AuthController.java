package com.example.pcconfigurator.web;

import com.example.pcconfigurator.config.JwtUtil;
import com.example.pcconfigurator.domain.User;
import com.example.pcconfigurator.repository.UserRepository;
import com.example.pcconfigurator.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

// Публичные эндпоинты для регистрации, входа в систему и получения JWT-токена
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public void register(@Valid @RequestBody RegisterRequest request) {
        userService.registerUser(request.getEmail(), request.getPassword());
    }

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user);
        return new TokenResponse(token);
    }

    @Data
    public static class RegisterRequest {
        @Email @NotBlank
        private String email;

        @NotBlank
        private String password;
    }

    @Data
    public static class LoginRequest {
        @Email @NotBlank
        private String email;

        @NotBlank
        private String password;
    }

    public record TokenResponse(String token) {}
}
