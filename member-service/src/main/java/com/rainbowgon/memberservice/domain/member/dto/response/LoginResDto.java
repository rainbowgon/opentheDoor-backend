package com.rainbowgon.memberservice.domain.member.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LoginResDto { // 로그인(회원가입) 성공 시, 반환할 데이터 -> 토큰, 닉네임, 프로필 사진

    private String accessToken;
    private String refreshToken;
    private String nickname;
    private String profileImage;

    public static LoginResDto of(String accessToken, String refreshToken, String nickname, String profileImage) {
        return LoginResDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .nickname(nickname)
                .profileImage(profileImage)
                .build();
    }

}
