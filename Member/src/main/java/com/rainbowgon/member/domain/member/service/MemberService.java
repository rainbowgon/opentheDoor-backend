package com.rainbowgon.member.domain.member.service;

import com.rainbowgon.member.domain.member.dto.request.MemberCreateRequestDto;
import com.rainbowgon.member.domain.member.dto.response.MemberTestResponseDto;

import java.util.UUID;

public interface MemberService {

    MemberTestResponseDto selectMemberById(UUID memberId);

    MemberTestResponseDto selectMemberByPhoneNumber(String phoneNumber);

    MemberTestResponseDto createMember(MemberCreateRequestDto createRequestDto);
}
