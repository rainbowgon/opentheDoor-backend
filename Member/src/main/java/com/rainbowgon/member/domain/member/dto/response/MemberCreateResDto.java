package com.rainbowgon.member.domain.member.dto.response;

import com.rainbowgon.member.global.security.dto.JwtTokenDto;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MemberCreateResDto { // 회원가입 요청에 대한 응답 객체

    //    private MemberInfoResDto memberInfo;
    private JwtTokenDto tokens;

}
