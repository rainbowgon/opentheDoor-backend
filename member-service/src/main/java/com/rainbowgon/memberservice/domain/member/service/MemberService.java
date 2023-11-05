package com.rainbowgon.memberservice.domain.member.service;

import com.rainbowgon.memberservice.domain.member.dto.request.MemberCreateReqDto;
import com.rainbowgon.memberservice.domain.member.dto.request.MemberUpdateReqDto;
import com.rainbowgon.memberservice.domain.member.dto.response.MemberInfoResDto;
import com.rainbowgon.memberservice.global.security.dto.JwtTokenDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface MemberService {

    JwtTokenDto createMember(MemberCreateReqDto createRequestDto);

    MemberInfoResDto selectMemberInfo(UUID memberId);

    Boolean updateMemberInfo(UUID memberId, MemberUpdateReqDto memberUpdateReqDto,
                             MultipartFile profileImage);

    void deleteMember(UUID memberId);

}