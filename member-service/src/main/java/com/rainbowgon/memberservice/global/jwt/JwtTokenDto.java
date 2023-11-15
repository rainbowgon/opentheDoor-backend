package com.rainbowgon.memberservice.global.jwt;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class JwtTokenDto {

    private String accessToken;
    private String refreshToken;

    public static JwtTokenDto of(String accessToken, String refreshToken) {
        return JwtTokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
