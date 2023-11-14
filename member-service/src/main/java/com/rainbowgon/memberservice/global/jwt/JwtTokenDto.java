package com.rainbowgon.memberservice.global.jwt;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtTokenDto {

    private String accessToken;
    private String refreshToken;

}
