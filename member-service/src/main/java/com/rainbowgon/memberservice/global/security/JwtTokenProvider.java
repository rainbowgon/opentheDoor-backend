package com.rainbowgon.memberservice.global.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${spring.jwt.expire.access-token}")
    private static long ACCESS_TOKEN_EXPIRE_TIME; // 30분
    @Value("${spring.jwt.expire.refresh-token}")
    private static long REFRESH_TOKEN_EXPIRE_TIME; // 7일
    @Value("${spring.jwt.secret}")
    private String JWT_SECRET_KEY;

    public String generateAccessToken(Long profileId) {
        return generateToken(profileId, ACCESS_TOKEN_EXPIRE_TIME);
    }

    public String generateRefreshToken(Long profileId) {
        return generateToken(profileId, REFRESH_TOKEN_EXPIRE_TIME);
    }

    private String generateToken(Long profileId, long expireTime) {

        log.info("[JwtTokenProvider] generateToken ... profileId = {}", profileId);
        log.info("[JwtTokenProvider] generateToken ... expireTime = {}", expireTime);

        Claims claims = Jwts.claims();
        claims.put("profileId", profileId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigningKey(JWT_SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
