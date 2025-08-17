package com.gigmework.controller;

import com.gigmework.model.User;
import com.gigmework.model.UserRole;
import com.gigmework.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

/**
 * Controller handling user registration and login. For demonstration
 * purposes this controller does not implement password-based
 * authentication. In a real system you would hash passwords and issue
 * JWTs on login.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Registers a new user. Accepts a JSON body representing the user.
     *
     * Example request:
     * {
     *   "email": "client@example.com",
     *   "name": "Client User",
     *   "role": "CLIENT"
     * }
     */
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    /**
     * Logs in a user by email. In a real application you would verify a
     * password or OTP and return a JWT. Here we simply return the user
     * record if it exists.
     */
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        return userOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Returns all freelancers registered in the system.
     *
     * @return list of freelancers
     */
    @GetMapping("/freelancers")
    public ResponseEntity<List<User>> listFreelancers() {
        return ResponseEntity.ok(userRepository.findByRole(UserRole.FREELANCER));
    }

    /**
     * Returns all clients registered in the system.
     *
     * @return list of clients
     */
    @GetMapping("/clients")
    public ResponseEntity<List<User>> listClients() {
        return ResponseEntity.ok(userRepository.findByRole(UserRole.CLIENT));
    }
}