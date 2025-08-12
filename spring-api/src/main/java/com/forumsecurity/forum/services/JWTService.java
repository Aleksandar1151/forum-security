package com.forumsecurity.forum.services;

import com.forumsecurity.forum.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {
    public static final String SECRET_KEY = "NovOcYBIYT3SXF/0VJKY6y+sUhq/fu0NMNeSVPwIbAJ+M67OsK/vZE0v9yME/lto"; // zamenite stvarnim tajnim ključem
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes()); // Create Key instance
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 sati
                .signWith(key)
                .compact();
    }

    // Validacija JWT tokena
    // Validate JWT token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder() // Use parserBuilder instead of deprecated parser()
                    .setSigningKey(key) // Use Key instance instead of String
                    .build()
                    .parseClaimsJws(token); // If parsing is successful, token is valid
            return true;
        } catch (Exception e) {
            // Token is invalid or expired
            return false;
        }
    }

    // Dobijanje korisničkog imena iz JWT tokena
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder() // Use parserBuilder instead of deprecated parser()
                .setSigningKey(key) // Use Key instance instead of String
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject(); // Returns the subject, typically the username
    }

}
