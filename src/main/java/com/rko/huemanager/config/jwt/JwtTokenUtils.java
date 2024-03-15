package com.rko.huemanager.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JwtTokenUtils {
    public static Boolean validate(String token, UserDetails userDetails, String key) {
        String email = getUsername(token, key);
        return email.equals(userDetails.getUsername()) && !isTokenExpired(token, key);
    }

    public static Claims extractAllClaims(String token, String key) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(key))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static Boolean isTokenExpired(String token, String key) {
        Date expiration = extractAllClaims(token, key).getExpiration();
        return expiration.before(new Date());
    }

    public static String getUsername(String token, String key) {
        return extractAllClaims(token, key).get("email", String.class);
    }

    public static String generateAccessToken(String email, String key, long expiredTimeMs){
        return doGenerateToken(email, expiredTimeMs, key);
    }

    public static long getRemainMilliSeconds(String token, String key) {
        Date expiration = extractAllClaims(token, key).getExpiration();
        return expiration.getTime() - new Date().getTime();
    }

    private static String doGenerateToken(String email, long expiredTime, String key) {
        Claims claims = Jwts.claims();
        claims.put("email", email);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTime))
                .signWith(SignatureAlgorithm.HS256, getSigningKey(key))
                .compact();
    }

    private static Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
