package com.rainbowgon.memberservice.domain.member.service;

import com.rainbowgon.memberservice.domain.member.dto.MemberDto;
import com.rainbowgon.memberservice.domain.member.dto.request.MemberCreateReqDto;
import com.rainbowgon.memberservice.domain.member.dto.request.MemberPhoneReqDto;
import com.rainbowgon.memberservice.domain.member.dto.request.MemberUpdateReqDto;
import com.rainbowgon.memberservice.domain.member.dto.response.BookerInfoResDto;
import com.rainbowgon.memberservice.domain.member.dto.response.MemberInfoResDto;
import com.rainbowgon.memberservice.global.client.dto.output.FcmTokenListOutDto;
import com.rainbowgon.memberservice.global.client.dto.output.FcmTokenOutDto;
import com.rainbowgon.memberservice.global.jwt.JwtTokenDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface MemberService {

    JwtTokenDto createMember(MemberCreateReqDto createRequestDto);

    String sendMessage(MemberPhoneReqDto memberPhoneReqDto);

    MemberInfoResDto selectMemberInfo(UUID memberId);

    void updateMemberInfo(UUID memberId, MemberUpdateReqDto memberUpdateReqDto, MultipartFile profileImage);

    void deleteMember(UUID memberId);

    BookerInfoResDto selectBookerInfo(String memberId);

    FcmTokenOutDto selectMemberFcmToken(String memberId);

    FcmTokenListOutDto selectMemberFcmTokenList(List<String> memberIdList);

    MemberDto findMemberByProviderId(String providerId);

}
