package com.finance.financeapp.controller;

import com.finance.financeapp.dto.UserRegistrationDto;
import com.finance.financeapp.model.User;
import com.finance.financeapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDto registrationDto) {
        //username validation
        String username= registrationDto.getUsername();
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (username.length() < 3 || username.length() > 20) {
            throw new IllegalArgumentException("Username must be between 3 and 20 characters");
        }
        User existingUser = userService.findByUsername(username);
        if (existingUser != null) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }

        //email validation
        String email= registrationDto.getEmail();
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        existingUser = userService.findByEmail(email);
        if (existingUser != null) {
            return ResponseEntity.badRequest().body("Email is already in use!");
        }

        //password validation
        String password=registrationDto.getPassword();
        String confirmPassword= registrationDto.getConfirmPassword();
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new IllegalArgumentException("Password must contain at least one lowercase letter");
        }
        if (!password.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Password must contain at least one number");
        }
        if (password.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body("Passwords do not match!");
        }




        userService.registerNewUser(
                registrationDto.getUsername(),
                registrationDto.getEmail(),
                registrationDto.getPassword()
        );
        return ResponseEntity.ok("User registered successfully!");
    }
}

