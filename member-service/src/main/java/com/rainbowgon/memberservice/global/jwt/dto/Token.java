package com.rainbowgon.memberservice.global.jwt.dto;

import lombok.Builder;
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

    @Builder
    public Token(Long profileId, String memberId, String accessToken, String refreshToken, String fcmToken,
                 Long expiration) {
        this.profileId = profileId;
        this.memberId = memberId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.fcmToken = fcmToken;
        this.expiration = expiration;
    }

}
