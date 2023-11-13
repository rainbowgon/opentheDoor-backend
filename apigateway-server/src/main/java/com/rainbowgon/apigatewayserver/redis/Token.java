package com.rainbowgon.apigatewayserver.redis;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@RedisHash("Token")
public class Token {

    @Id
    private Long profileId;
    private String memberId; // UUID
    private String accessToken;
    private String refreshToken;
    private String fcmToken;
    @TimeToLive
    private Long expiration;

}
