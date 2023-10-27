package com.rainbowgon.member.domain.member.dto.response;

import com.rainbowgon.member.domain.member.entity.Member;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MemberTestResponseDto {

    private String memberId;
    private String name;
    private String phoneNumber;
    private LocalDate birthDate;

    public static MemberTestResponseDto from(Member member) {
        return MemberTestResponseDto.builder()
                .memberId(member.getId().toString())
                .name(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .birthDate(member.getBirthDate())
                .build();
    }
}
