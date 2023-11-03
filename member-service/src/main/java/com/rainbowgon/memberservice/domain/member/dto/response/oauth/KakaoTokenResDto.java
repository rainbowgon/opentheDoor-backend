package com.rainbowgon.memberservice.domain.member.dto.response.oauth;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoTokenResDto {

    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private String idToken;
    private Integer expiresIn;
    private String scope;
    private Integer refreshTokenExpiresIn;

}
