package com.rainbowgon.memberservice.global.client.dto.output;

import com.rainbowgon.memberservice.global.redis.dto.Token;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class FcmTokenOutDto { // reservation-service response

    private String memberId;
    private String fcmToken;

    public static FcmTokenOutDto from(Token token) {
        return FcmTokenOutDto.builder()
                .memberId(token.getMemberId())
                .fcmToken(token.getFcmToken())
                .build();
    }

}
