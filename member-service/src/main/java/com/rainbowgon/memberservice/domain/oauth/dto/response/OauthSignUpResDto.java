package com.rainbowgon.memberservice.domain.oauth.dto.response;

import com.rainbowgon.memberservice.domain.member.entity.Provider;
import com.rainbowgon.memberservice.domain.oauth.dto.KakaoUserInfoDto;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OauthSignUpResDto { // kakao, google에서 받은 정보

    private Provider provider;
    private String providerId;
    private String nickname;
    private String profileImage;

    public static OauthSignUpResDto fromKakao(KakaoUserInfoDto kakaoUserInfoDto) {
        return OauthSignUpResDto.builder()
                .provider(Provider.KAKAO)
                .providerId(kakaoUserInfoDto.getId())
                .nickname(kakaoUserInfoDto.getProperties().getNickname())
                .profileImage(kakaoUserInfoDto.getProperties().getProfileImage())
                .build();
    }

}
