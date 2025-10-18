package com.himalayanwc.auth.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    public String generateJwtToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuer("sonu coding")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10)) // 10 minutes
                .signWith(generateKey())
                .compact();
    }
    private SecretKey generateKey() {
        String secretKey = "13df49cfa2d32a5474c54d9a5819e3a2cd6b363b5456e2849d189d142084553d";
        byte[] decode = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(decode);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String email = extractUserEmail(token);
            return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
    private boolean isTokenExpired(String token) {
        try {
            Date expiration = extractClaims(token).getExpiration();
            return new Date().after(expiration);
        } catch (ExpiredJwtException e) {
            return true;
        }
    }
    public String extractUserEmail(String token) throws JwtException{
        try {
            Claims claims = extractClaims(token);
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(null, null, "Token has expired");
        }
        catch (JwtException e){
            throw new JwtException("Token validation failed: " + e.getMessage());
        }
    }
    private Claims extractClaims(String token) throws JwtException{
         return    Jwts.parser()
                    .verifyWith(generateKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
    }
}