package com.rainbowgon.memberservice.domain.profile.dto.response;

import com.rainbowgon.memberservice.domain.profile.entity.Profile;
import com.rainbowgon.memberservice.global.entity.NotificationStatus;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProfileSimpleResDto {

    private Long profileId;
    private String nickname;
    private String profileImage;
    private NotificationStatus bookmarkNotificationStatus;

    public static ProfileSimpleResDto from(Profile profile, String profileImageUrl) {
        return ProfileSimpleResDto.builder()
                .profileId(profile.getId())
                .nickname(profile.getNickname())
                .profileImage(profileImageUrl)
                .bookmarkNotificationStatus(profile.getBookmarkNotificationStatus())
                .build();
    }

}
