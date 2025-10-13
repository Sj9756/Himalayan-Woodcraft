package com.himalayanwc.auth.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {
    public String generateJwtToken(String email){
      return   Jwts.builder()
                .subject(email)
                .issuer("sonu coding")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*10))
                .signWith(generateKey())
                .compact();
    }

    private Key generateKey() {
        String secretKey = "13df49cfa2d32a5474c54d9a5819e3a2cd6b363b5456e2849d189d142084553d";
        byte[] decode = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(decode);
    }

}
