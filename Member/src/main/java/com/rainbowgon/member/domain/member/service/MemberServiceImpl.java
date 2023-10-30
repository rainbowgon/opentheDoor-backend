package com.rainbowgon.member.domain.member.service;


import com.rainbowgon.member.domain.member.dto.request.MemberCreateReqDto;
import com.rainbowgon.member.domain.member.dto.response.MemberCreateResDto;
import com.rainbowgon.member.domain.member.dto.response.MemberInfoResDto;
import com.rainbowgon.member.domain.member.dto.response.MemberTestResDto;
import com.rainbowgon.member.domain.member.entity.Member;
import com.rainbowgon.member.domain.member.repository.MemberRepository;
import com.rainbowgon.member.domain.profile.entity.Profile;
import com.rainbowgon.member.domain.profile.service.ProfileService;
import com.rainbowgon.member.global.error.exception.MemberNotFoundException;
import com.rainbowgon.member.global.error.exception.MemberPhoneNumberDuplicationException;
import com.rainbowgon.member.global.security.JwtTokenProvider;
import com.rainbowgon.member.global.security.dto.JwtTokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final JwtTokenProvider jwtTokenProvider;
    private final ProfileService profileService;

    @Override
    public MemberCreateResDto createMember(MemberCreateReqDto createReqDto) {

        // 중복 체크
        if (checkExistPhoneNumber(createReqDto.getPhoneNumber())) {
            throw MemberPhoneNumberDuplicationException.EXCEPTION;
        }

        // 클라이언트한테 받은 정보로 멤버 객체 생성 후 DB에 저장
        Member member = memberRepository.save(
                Member.builder()
                        .name(createReqDto.getName())
                        .phoneNumber(createReqDto.getPhoneNumber())
                        .provider(createReqDto.getProvider())
                        .providerId(createReqDto.getProviderId())
                        .birthDate(createReqDto.getBirthDate())
                        .build());

        // 생성한 멤버 객체로 프로필 객체 생성
        Profile profile = profileService.createProfile(member, createReqDto.getNickname(), createReqDto.getProfileImage());

        // accessToken과 refreshToken 생성
        JwtTokenDto jwtTokenDto = JwtTokenDto.builder()
                .accessToken(jwtTokenProvider.generateAccessToken(member.getId()))
                .refreshToken(jwtTokenProvider.generateRefreshToken(member.getId()))
                .build();

        // TODO redis에 refreshToken 저장

        return MemberCreateResDto.builder()
                .memberInfo(MemberInfoResDto.from(member, profile))
                .tokens(jwtTokenDto)
                .build();
    }

    /**
     * 이미 존재하는 회원인지 확인 (전화번호 중복 체크)
     */
    private boolean checkExistPhoneNumber(String phoneNumber) {
        return memberRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public MemberTestResDto selectMemberById(UUID memberId) {
        log.debug("[MemberServiceImpl] selectMemberById 로직 start");
        return memberRepository.findById(memberId)
                .map(MemberTestResDto::from)
                .orElseThrow(MemberNotFoundException::new);
    }

    @Override
    public MemberTestResDto selectMemberByPhoneNumber(String phoneNumber) {
        return memberRepository.findByPhoneNumber(phoneNumber)
                .map(MemberTestResDto::from)
                .orElseThrow(MemberNotFoundException::new);
    }

}
