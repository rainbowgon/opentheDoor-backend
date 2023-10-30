package com.rainbowgon.member.domain.member.service;

import com.rainbowgon.member.domain.member.dto.request.MemberCreateReqDto;
import com.rainbowgon.member.domain.member.dto.request.MemberUpdateReqDto;
import com.rainbowgon.member.domain.member.dto.response.MemberCreateResDto;
import com.rainbowgon.member.domain.member.dto.response.MemberInfoResDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface MemberService {

    MemberCreateResDto createMember(MemberCreateReqDto createRequestDto);

    MemberInfoResDto selectMemberInfo(UUID memberId);

    Boolean updateMemberInfo(UUID memberId, MemberUpdateReqDto memberUpdateReqDto, MultipartFile profileImage);
    
}
