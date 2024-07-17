package com.api_contact_manager.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtUtils {
    @Value("${app.jwt-secret-key}")
    private String jwtSecretKey;
    @Value("${app.jwt-expiration-time}")
    private String jwtExpirationTime;

    @Autowired
    private UserService userService;

    public String generateJwt(String username) {
        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + Integer.parseInt(jwtExpirationTime);
        UserDetails user = userService.loadUserByUsername(username);

//        final Map<String, Object> claims = Map.of(
//                Claims.EXPIRATION, new Date(expirationTime),
//                Claims.SUBJECT, user.getUsername()
//        );

        return Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .setSubject(user.getUsername())
//                .setClaims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = getClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    private <T> T getClaim(String token, Function<Claims, T> function) {
        Claims claims = getAllClaims(token);
        return function.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getKey() {
        final byte[] decoder = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(decoder);
    }
}
