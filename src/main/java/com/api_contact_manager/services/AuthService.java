package com.api_contact_manager.services;

import com.api_contact_manager.dto.AuthenticateDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthService {
    private AuthenticationManager authenticationManager;
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
