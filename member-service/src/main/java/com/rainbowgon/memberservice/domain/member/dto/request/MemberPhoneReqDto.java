package com.rainbowgon.memberservice.domain.member.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberPhoneReqDto {

    private String phoneNumber;
    // +통신사
    
}
