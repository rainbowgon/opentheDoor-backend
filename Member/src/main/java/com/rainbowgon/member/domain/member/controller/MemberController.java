package com.rainbowgon.member.domain.member.controller;

import com.rainbowgon.member.domain.member.dto.request.MemberCreateRequestDto;
import com.rainbowgon.member.domain.member.dto.response.MemberTestResponseDto;
import com.rainbowgon.member.domain.member.entity.Member;
import com.rainbowgon.member.domain.member.service.MemberService;
import com.rainbowgon.member.global.security.JwtTokenProvider;
import com.rainbowgon.member.global.security.dto.JwtTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/me")
    public ResponseEntity<MemberTestResponseDto> selectMemberById(@AuthenticationPrincipal Member member) {

        MemberTestResponseDto selectedMember = memberService.selectMemberById(member.getId());

        return ResponseEntity.ok(selectedMember);
    }

    @GetMapping("/{phone-number}")
    public ResponseEntity<MemberTestResponseDto> selectMemberByPhoneNumber(@PathVariable("phone-number") String phoneNumber) {

        MemberTestResponseDto selectedMember = memberService.selectMemberByPhoneNumber(phoneNumber);

        return ResponseEntity.ok(selectedMember);
    }

    @PostMapping("/signup")
    public ResponseEntity<MemberTestResponseDto> createMember(@RequestBody MemberCreateRequestDto createRequestDto) {

        MemberTestResponseDto createdMember = memberService.createMember(createRequestDto);

        JwtTokenDto jwtTokenDto = JwtTokenDto.builder()
                .accessToken(jwtTokenProvider.generateAccessToken(createdMember.getMemberId()))
                .refreshToken(jwtTokenProvider.generateRefreshToken(createdMember.getMemberId()))
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtTokenDto.getAccessToken());

        return ResponseEntity.ok().headers(headers).body(createdMember);
    }
}
