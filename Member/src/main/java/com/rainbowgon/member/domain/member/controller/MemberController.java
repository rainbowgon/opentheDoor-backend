package com.rainbowgon.member.domain.member.controller;

import com.rainbowgon.member.domain.member.dto.request.MemberCreateReqDto;
import com.rainbowgon.member.domain.member.dto.request.MemberUpdateReqDto;
import com.rainbowgon.member.domain.member.dto.response.MemberInfoResDto;
import com.rainbowgon.member.domain.member.dto.response.oauth.KakaoProfileResDto;
import com.rainbowgon.member.domain.member.dto.response.oauth.OAuthProfileResDto;
import com.rainbowgon.member.domain.member.service.KakaoLoginService;
import com.rainbowgon.member.domain.member.service.MemberService;
import com.rainbowgon.member.global.response.JsonResponse;
import com.rainbowgon.member.global.response.ResponseWrapper;
import com.rainbowgon.member.global.security.dto.JwtTokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final KakaoLoginService kakaoLoginService;


    /**
     * 카카오 로그인
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
    public ResponseEntity<ResponseWrapper<JwtTokenDto>> createMember(@RequestBody MemberCreateReqDto createReqDto) {

        JwtTokenDto jwtTokenDto = memberService.createMember(createReqDto);

        return JsonResponse.ok("회원가입에 성공하였습니다.", jwtTokenDto);
    }

    /**
     * 회원 정보 조회
     * 회원 정보 수정 요청 시 보내줄 데이터
     */
    @GetMapping("/me")
    public ResponseEntity<ResponseWrapper<MemberInfoResDto>> selectMemberInfo(@AuthenticationPrincipal String memberId) {

        MemberInfoResDto memberInfoResDto = memberService.selectMemberInfo(UUID.fromString(memberId));

        return JsonResponse.ok("회원 정보가 성공적으로 조회되었습니다.", memberInfoResDto);
    }

    /**
     * 회원 정보 수정
     * 이름, 닉네임, 전화번호, 생년월일, 프로필사진
     */
    @PatchMapping
    public ResponseEntity<ResponseWrapper<Nullable>> updateMemberInfo(
            @AuthenticationPrincipal String memberId,
            @RequestPart(value = "info") MemberUpdateReqDto memberUpdateReqDto,
            @RequestPart(value = "file", required = false) MultipartFile profileImage) {

        log.info("[MemberController] 회원 정보 수정 로직 start");
        memberService.updateMemberInfo(UUID.fromString(memberId), memberUpdateReqDto, profileImage);

        return JsonResponse.ok("회원 정보가 성공적으로 수정되었습니다.");
    }

}
