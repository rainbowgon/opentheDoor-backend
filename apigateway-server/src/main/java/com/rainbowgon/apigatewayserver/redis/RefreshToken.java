package com.rainbowgon.apigatewayserver.redis;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@RedisHash("refreshToken")
public class RefreshToken {

    @Id
    private String memberId; // UUID
    private String accessToken;
    private String refreshToken;
    @TimeToLive
    private Long expiration;

    @Builder
    public RefreshToken(String memberId, String accessToken, String refreshToken, Long expiration) {
        this.memberId = memberId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiration = expiration;
    }
}
