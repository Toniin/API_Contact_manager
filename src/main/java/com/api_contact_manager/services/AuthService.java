package com.api_contact_manager.services;

import com.api_contact_manager.dto.AuthenticateDTO;
import com.api_contact_manager.models.User;
import com.api_contact_manager.repositories.UserRepository;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

    public Map<String, String> authenticateUser(AuthenticateDTO authenticateDTO) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticateDTO.username(), authenticateDTO.password())
        );

        if (authenticate.isAuthenticated()) {
            String token = jwtUtils.generateJwt(authenticateDTO.username());
            return Map.of("username", authenticateDTO.username(), "token", token);
        }

        return Collections.emptyMap();
    }
}
