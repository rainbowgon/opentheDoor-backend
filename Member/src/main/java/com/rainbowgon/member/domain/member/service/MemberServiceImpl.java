package com.rainbowgon.member.domain.member.service;


import com.rainbowgon.member.domain.member.dto.request.MemberCreateRequestDto;
import com.rainbowgon.member.domain.member.dto.response.MemberTestResponseDto;
import com.rainbowgon.member.domain.member.entity.Member;
import com.rainbowgon.member.domain.member.repository.MemberRepository;
import com.rainbowgon.member.global.error.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public MemberTestResponseDto selectMemberById(UUID memberId) {
        log.debug("[MemberServiceImpl] selectMemberById 로직 start");
        return memberRepository.findById(memberId)
                .map(MemberTestResponseDto::from)
                .orElseThrow(MemberNotFoundException::new);
    }

    @Override
    public MemberTestResponseDto selectMemberByPhoneNumber(String phoneNumber) {
        return memberRepository.findByPhoneNumber(phoneNumber)
                .map(MemberTestResponseDto::from)
                .orElseThrow(MemberNotFoundException::new);
    }

    @Override
    public MemberTestResponseDto createMember(MemberCreateRequestDto createRequestDto) {

        Member member = Member.builder()
                .name(createRequestDto.getName())
                .phoneNumber(createRequestDto.getPhoneNumber())
                .build();

        return MemberTestResponseDto.from(memberRepository.save(member));
    }

}
