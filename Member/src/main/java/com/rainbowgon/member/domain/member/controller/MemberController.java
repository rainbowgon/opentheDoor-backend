package com.rainbowgon.member.domain.member.controller;

import com.rainbowgon.member.domain.member.dto.request.MemberCreateReqDto;
import com.rainbowgon.member.domain.member.dto.response.KakaoProfileResDto;
import com.rainbowgon.member.domain.member.dto.response.MemberCreateResDto;
import com.rainbowgon.member.domain.member.dto.response.MemberTestResDto;
import com.rainbowgon.member.domain.member.dto.response.OAuthProfileResDto;
import com.rainbowgon.member.domain.member.service.KakaoLoginService;
import com.rainbowgon.member.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final KakaoLoginService kakaoLoginService;


    /**
     * 카카오로 로그인
     */
    @GetMapping("/login/kakao")
    public ResponseEntity<OAuthProfileResDto> kakaoLogin(@RequestParam("code") String code) throws Exception {

        String kakaoAccessToken = kakaoLoginService.getToken(code);
        KakaoProfileResDto kakaoProfileResDto = kakaoLoginService.getProfile(kakaoAccessToken);

        return ResponseEntity.ok(OAuthProfileResDto.fromKakao(kakaoProfileResDto));
    }

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<MemberCreateResDto> createMember(@RequestBody MemberCreateReqDto createReqDto) {

        MemberCreateResDto memberCreateResDto = memberService.createMember(createReqDto);

        return ResponseEntity.ok(memberCreateResDto);
    }

    @GetMapping("/me")
    public ResponseEntity<MemberTestResDto> selectMemberById(@AuthenticationPrincipal String memberId) {

        MemberTestResDto selectedMember = memberService.selectMemberById(UUID.fromString(memberId));

        return ResponseEntity.ok(selectedMember);
    }

    @GetMapping("/{phone-number}")
    public ResponseEntity<MemberTestResDto> selectMemberByPhoneNumber(@PathVariable("phone-number") String phoneNumber) {

        MemberTestResDto selectedMember = memberService.selectMemberByPhoneNumber(phoneNumber);

        return ResponseEntity.ok(selectedMember);
    }


}
