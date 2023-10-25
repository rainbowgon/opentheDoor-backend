package com.rainbowgon.member.domain.member.controller;

import com.rainbowgon.member.domain.member.dto.response.MemberTestResponseDto;
import com.rainbowgon.member.domain.member.entity.Member;
import com.rainbowgon.member.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

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
}
