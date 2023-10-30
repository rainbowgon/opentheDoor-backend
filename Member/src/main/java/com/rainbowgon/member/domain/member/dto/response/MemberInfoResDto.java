package com.rainbowgon.member.domain.member.dto.response;

import com.rainbowgon.member.domain.member.entity.Member;
import com.rainbowgon.member.domain.profile.entity.Profile;
import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MemberInfoResDto { // 회원 정보가 담긴 응답 객체

    private UUID memberId;
    private String nickname;
    private String profileImage;

    public static MemberInfoResDto of(Member member, Profile profile) {
        return MemberInfoResDto.builder()
                .memberId(member.getId())
                .nickname(profile.getNickname())
                .profileImage(profile.getProfileImage())
                .build();
    }
}
