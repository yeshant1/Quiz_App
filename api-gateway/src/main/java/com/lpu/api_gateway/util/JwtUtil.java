package com.lpu.api_gateway.util;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import io.jsonwebtoken.io.Decoders;

@Component
public class JwtUtil {

    private final String secretKey = "U2VjdXJlS2V5MTIzIT8kKiYoQCMkXjY1NDMyMTBAc2VjdXJpdHk=";

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired: " + e.getMessage());
            throw e;
        } catch (MalformedJwtException e) {
            System.out.println("Malformed token: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.out.println("Token invalid: " + e.getMessage());
            throw e;
        }
    }

    public String extractRole(String token) {
    	String role = extractAllClaims(token).get("role", String.class);
        System.out.println("Extracted Role: " + role);  // Log the role
        return role;
          
    }

    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
