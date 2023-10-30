package com.rainbowgon.member.domain.member.service;

import com.rainbowgon.member.domain.member.dto.request.MemberCreateReqDto;
import com.rainbowgon.member.domain.member.dto.response.MemberCreateResDto;
import com.rainbowgon.member.domain.member.dto.response.MemberTestResDto;

import java.util.UUID;

public interface MemberService {

    MemberTestResDto selectMemberById(UUID memberId);

    MemberTestResDto selectMemberByPhoneNumber(String phoneNumber);

    MemberCreateResDto createMember(MemberCreateReqDto createRequestDto);
}
