package com.rainbowgon.member.domain.member.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCreateRequestDto {

    private String name;
    private String phoneNumber;
    
}
