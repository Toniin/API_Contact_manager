package com.api_contact_manager.controllers;

import com.api_contact_manager.dto.AuthenticateDTO;
import com.api_contact_manager.models.User;
import com.api_contact_manager.services.AuthService;
import com.api_contact_manager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contacts/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userService.isUserExists(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        } else {
            return ResponseEntity.ok(userService.createUser(user));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticateDTO authenticateDTO) {
        try {
            return ResponseEntity.ok(authService.authenticateUser(authenticateDTO));
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
    }
}
