package com.resumeai.util;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
@SuppressWarnings("unused")
public class JwtUtil {
//	   private static final String SECRET = "THIS_IS_A_SECRET_KEY_FOR_JWT_1234567890";
//	    private static final long EXPIRATION = 1000 * 60 * 60 * 10; // 10 hours
//
//	    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());
//
//	    public String generateToken(String email) {
//	        return Jwts.builder()
//	                .setSubject(email)
//	                .setIssuedAt(new Date())
//	                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
//	                .signWith(key, SignatureAlgorithm.HS256)
//	                .compact();
//	    }
//
//	    public String extractEmail(String token) {
//	        return Jwts.parserBuilder()
//	                .setSigningKey(key)
//	                .build()
//	                .parseClaimsJws(token)
//	                .getBody()
//	                .getSubject();
//	    }
//
//	    public boolean isTokenValid(String token) {
//	        try {
//	            extractEmail(token);
//	            return true;
//	        } catch (Exception e) {
//	            return false;
//	        }
//	    }
//	
}
