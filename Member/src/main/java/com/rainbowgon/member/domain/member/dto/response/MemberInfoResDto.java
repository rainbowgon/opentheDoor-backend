package com.rainbowgon.member.domain.member.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rainbowgon.member.domain.profile.entity.Profile;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MemberInfoResDto { // 개인정보 수정 화면에 뿌려줄 데이터

    private Long profileId;
    private String profileImage;
    private String name;
    private String phoneNumber;
    private String nickname;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    public static MemberInfoResDto from(Profile profile) {
        return MemberInfoResDto.builder()
                .profileId(profile.getId())
                .profileImage(profile.getProfileImage())
                .name(profile.getMember().getName())
                .phoneNumber(profile.getMember().getPhoneNumber())
                .nickname(profile.getNickname())
                .birthDate(profile.getMember().getBirthDate())
                .build();
    }
}
