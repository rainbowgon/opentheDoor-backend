package com.rainbowgon.member.domain.member.service;


import com.rainbowgon.member.domain.member.dto.response.MemberTestResponseDto;
import com.rainbowgon.member.domain.member.repository.MemberRepository;
import com.rainbowgon.member.global.error.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public MemberTestResponseDto selectMemberById(UUID memberId) {
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

}
