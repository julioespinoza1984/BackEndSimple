package edu.jespinoza.security.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

public interface JwtService {
    String generateToken(String username);

    boolean validateToken(String token, UserDetails userDetails);

    String extractUsername(String token);

    boolean isTokenExpired(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
}
