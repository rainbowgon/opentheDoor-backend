package com.rainbowgon.member.domain.member.dto.response;

import com.rainbowgon.member.domain.member.entity.Provider;
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

}
