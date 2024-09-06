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
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("Passwords do not match!");
        }

        User existingUser = userService.findByUsername(registrationDto.getUsername());
        if (existingUser != null) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }

        existingUser = userService.findByEmail(registrationDto.getEmail());
        if (existingUser != null) {
            return ResponseEntity.badRequest().body("Email is already in use!");
        }

        userService.registerNewUser(
                registrationDto.getUsername(),
                registrationDto.getEmail(),
                registrationDto.getPassword()
        );
        return ResponseEntity.ok("User registered successfully!");
    }
}

