package com.rainbowgon.memberservice.global.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
public class JwtTokenProvider {

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30; // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 7일

    @Value("${jwt.secret}")
    private String JWT_SECRET_KEY;

    public String generateAccessToken(UUID memberId) {
        return generateToken(memberId, ACCESS_TOKEN_EXPIRE_TIME);
    }

    public String generateRefreshToken(UUID memberId) {
        return generateToken(memberId, REFRESH_TOKEN_EXPIRE_TIME);
    }

    private String generateToken(UUID memberId, long expireTime) {

        Claims claims = Jwts.claims();
        claims.put("memberId", memberId.toString());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigningKey(JWT_SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(JWT_SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getMemberId(String token) {
        return extractAllClaims(token).get("memberId", String.class);
    }

    public Boolean validateToken(String token) {

        Date expiration = extractAllClaims(token).getExpiration();
        log.info("[JwtTokenProvider] Token Expiration = " + expiration);

        return expiration.before(new Date());
    }

}
