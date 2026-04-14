package com.ayush.College_Management_System.security.auth;

import com.ayush.College_Management_System.exception.UserAlreadyExistsException;
import com.ayush.College_Management_System.exception.UserNotFoundException;
import com.ayush.College_Management_System.security.jwt.JwtService;
import com.ayush.College_Management_System.security.user.User;
import com.ayush.College_Management_System.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    // SIGNUP
    public AuthResponse signup(SignupRequest request) {

        log.info("Signup attempt for username: {}", request.getUsername());

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            log.warn("Signup failed - user already exists: {}", request.getUsername());
            throw new UserAlreadyExistsException("User already exists with username: " + request.getUsername());
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword())) // ✅ encoded
                .role(request.getRole())                                 // ✅ direct enum, no valueOf
                .linkedId(request.getLinkedId())
                .build();

        userRepository.save(user);
        log.info("User registered successfully: {}", user.getUsername());

        String token = jwtService.generateToken(user);
        return new AuthResponse(token,"Bearer", user.getUsername(), user.getRole().name());
    }

    // LOGIN
    public AuthResponse login(LoginRequest request) {

        log.info("Login attempt for username: {}", request.getUsername());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        }

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found: " + request.getUsername()));

        String token = jwtService.generateToken(user);
        log.info("Login successful for username: {}", user.getUsername());

        return new AuthResponse(token,"Bearer", user.getUsername(), user.getRole().name());
    }
}