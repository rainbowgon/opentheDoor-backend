package com.rainbowgon.member.global.security.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtTokenDto {

    private String accessToken;
    private String refreshToken;

}
