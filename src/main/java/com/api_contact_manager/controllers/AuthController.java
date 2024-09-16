package com.api_contact_manager.controllers;

import com.api_contact_manager.dto.AuthenticateDTO;
import com.api_contact_manager.models.User;
import com.api_contact_manager.services.AuthService;
import com.api_contact_manager.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${base.url}/auth")
@AllArgsConstructor
public class AuthController {
    private UserService userService;
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userService.isUserExists(user.getUsername())) {
            String errorJson = """
                    {
                        "isError": true,
                        "message":"Username already exists"
                    }
                    """;

            return ResponseEntity
                    .badRequest()
                    .body(errorJson);
        } else {
            return ResponseEntity
                    .ok(userService.createUser(user));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticateDTO authenticateDTO) {
        try {
            return ResponseEntity.ok(authService.authenticateUser(authenticateDTO));
        } catch (BadCredentialsException e) {
            String errorJson = """
                    {
                        "isError": true,
                        "message":"Invalid username or password"
                    }
                    """;

            return ResponseEntity.badRequest().body(errorJson);
        }
    }
}
