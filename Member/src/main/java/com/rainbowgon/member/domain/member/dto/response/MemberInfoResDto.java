package com.rainbowgon.member.domain.member.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rainbowgon.member.domain.member.entity.Member;
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

    public static MemberInfoResDto from(Member member) {
        return MemberInfoResDto.builder()
                .profileId(member.getProfile().getId())
                .profileImage(member.getProfile().getProfileImage())
                .name(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .nickname(member.getProfile().getNickname())
                .birthDate(member.getBirthDate())
                .build();
    }
}
