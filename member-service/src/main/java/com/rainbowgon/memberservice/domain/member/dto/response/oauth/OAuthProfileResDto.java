package com.rainbowgon.memberservice.domain.member.dto.response.oauth;

import com.rainbowgon.memberservice.domain.member.entity.Provider;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OAuthProfileResDto { // OAuth를 통해 가져온 정보 객체 (닉네임, 프사)

    private Provider provider;
    private String providerId;
    private String nickname;
    private String profileImage;

    public static OAuthProfileResDto fromKakao(KakaoProfileResDto kakaoProfileResDto) {
        return OAuthProfileResDto.builder()
                .provider(Provider.KAKAO)
                .providerId(kakaoProfileResDto.getId())
                .nickname(kakaoProfileResDto.getProperties().getNickname())
                .profileImage(kakaoProfileResDto.getProperties().getProfileImage())
                .build();
    }

}