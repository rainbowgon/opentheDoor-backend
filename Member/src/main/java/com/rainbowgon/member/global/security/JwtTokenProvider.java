package com.rainbowgon.member.global.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    private static final String AUTHORITIES_KEY = "Auth";
    private static final String BEARER_TYPE = "Bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30; // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; //7일

    @Value("${jwt.secret}")
    private String JWT_SECRET_KEY;

//    public TokenDto generateTokenDto(Authentication authentication) {
//
//        String authorities = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(","));
//
//        long now = (new Date()).getTime();
//
//        Date tokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
//
//        String accessToken = Jwts.builder()
//                .setSubject(authentication.getName())
//                .claim(AUTHORITIES_KEY, authorities)
//                .setExpiration(tokenExpiresIn)
//                .signWith(SignatureAlgorithm.HS512, secretKey)
//                .compact();
//
//        return TokenDto.builder()
//                .grantType(BEARER_TYPE)
//                .accessToken(accessToken)
//                .tokenExpiresIn(tokenExpiresIn.getTime())
//                .build();
//    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(JWT_SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getMemberId(String token) {
        return extractAllClaims(token).get("memberId", String.class);
    }

    public Boolean validateToken(String token) {
        Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
