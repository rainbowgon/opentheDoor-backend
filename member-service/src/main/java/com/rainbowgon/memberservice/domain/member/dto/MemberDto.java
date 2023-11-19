package com.rainbowgon.memberservice.domain.member.dto;

import com.rainbowgon.memberservice.domain.member.entity.Member;
import com.rainbowgon.memberservice.domain.profile.entity.Profile;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDto {

    private String memberId;
    private Long profileId;
    private String nickname;
    private String profileImage;

    public static MemberDto of(Member member, Long profileId) {
        return MemberDto.builder()
                .memberId(member.getId().toString())
                .profileId(profileId)
                .build();
    }

    public static MemberDto from(Profile profile) {
        return MemberDto.builder()
                .memberId(String.valueOf(profile.getMember().getId()))
                .profileId(profile.getId())
                .nickname(profile.getNickname())
                .profileImage(profile.getProfileImage())
                .build();
    }

}
