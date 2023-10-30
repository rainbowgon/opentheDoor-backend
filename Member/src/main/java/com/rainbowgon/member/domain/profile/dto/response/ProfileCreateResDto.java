package com.rainbowgon.member.domain.profile.dto.response;

import com.rainbowgon.member.domain.profile.entity.Profile;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProfileCreateResDto {

    private String nickname;
    private String profileImage;

    public static ProfileCreateResDto from(Profile profile) {
        return ProfileCreateResDto.builder()
                .nickname(profile.getNickname())
                .profileImage(builder().profileImage)
                .build();
    }

}
