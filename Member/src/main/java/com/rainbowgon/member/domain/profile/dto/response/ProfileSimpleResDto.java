package com.rainbowgon.member.domain.profile.dto.response;

import com.rainbowgon.member.domain.profile.entity.Profile;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProfileSimpleResDto {

    private Long profileId;
    private String nickname;
    private String profileImage;

    public static ProfileSimpleResDto from(Profile profile) {
        return ProfileSimpleResDto.builder()
                .profileId(profile.getId())
                .nickname(profile.getNickname())
                .profileImage(profile.getProfileImage())
                .build();
    }

}
