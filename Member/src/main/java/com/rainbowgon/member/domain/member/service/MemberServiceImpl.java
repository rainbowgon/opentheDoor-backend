package com.rainbowgon.member.domain.member.service;


import com.rainbowgon.member.domain.member.dto.response.MemberTestResponseDto;
import com.rainbowgon.member.domain.member.repository.MemberRepository;
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
                .orElseThrow(() -> new RuntimeException("해당 아이디의 유저가 없습니다."));
    }

    @Override
    public MemberTestResponseDto selectMemberByPhoneNumber(String phoneNumber) {
        return memberRepository.findByPhoneNumber(phoneNumber)
                .map(MemberTestResponseDto::from)
                .orElseThrow(() -> new RuntimeException("해당 전화번호를 가진 유저가 없습니다."));
    }
    
}
