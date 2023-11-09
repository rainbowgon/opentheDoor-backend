package com.rainbowgon.memberservice.domain.member.controller;

import com.rainbowgon.memberservice.domain.member.dto.request.MemberCreateReqDto;
import com.rainbowgon.memberservice.domain.member.dto.request.MemberPhoneReqDto;
import com.rainbowgon.memberservice.domain.member.dto.request.MemberUpdateReqDto;
import com.rainbowgon.memberservice.domain.member.dto.response.MemberInfoResDto;
import com.rainbowgon.memberservice.domain.member.dto.response.oauth.KakaoProfileResDto;
import com.rainbowgon.memberservice.domain.member.dto.response.oauth.OAuthProfileResDto;
import com.rainbowgon.memberservice.domain.member.service.KakaoLoginService;
import com.rainbowgon.memberservice.domain.member.service.MemberService;
import com.rainbowgon.memberservice.global.client.NotificationServiceClient;
import com.rainbowgon.memberservice.global.response.JsonResponse;
import com.rainbowgon.memberservice.global.response.ResponseWrapper;
import com.rainbowgon.memberservice.global.security.dto.JwtTokenDto;
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
    private final NotificationServiceClient notificationServiceClient;


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
     * 전화번호 본인 인증
     */
    @PostMapping("/phone")
    public ResponseEntity<ResponseWrapper<String>> sendMessage(@RequestBody MemberPhoneReqDto memberPhoneReqDto) {

        String checkNumber = memberService.sendMessage(memberPhoneReqDto);

        return JsonResponse.ok("인증번호를 발송했습니다.", checkNumber);
    }

    /**
     * 개인 정보 조회
     * 개인 정보 수정 요청 시 보내줄 데이터
     */
    @GetMapping("/me")
    public ResponseEntity<ResponseWrapper<MemberInfoResDto>> selectMemberInfo(
            @AuthenticationPrincipal String memberId) {

        MemberInfoResDto memberInfoResDto = memberService.selectMemberInfo(UUID.fromString(memberId));

        return JsonResponse.ok("개인 정보가 성공적으로 조회되었습니다.", memberInfoResDto);
    }

    /**
     * 개인 정보 수정
     * 수정 가능한 데이터 -> 이름, 닉네임, 전화번호, 생년월일, 프로필사진
     * 이름은 수정 사항 없어도 기존값 그대로, 나머지는 수정 사항 없으면 null
     */
    @PatchMapping
    public ResponseEntity<ResponseWrapper<Nullable>> updateMemberInfo(
            @AuthenticationPrincipal String memberId,
            @RequestPart(value = "info") MemberUpdateReqDto memberUpdateReqDto,
            @RequestPart(value = "file", required = false) MultipartFile profileImage) {

        memberService.updateMemberInfo(UUID.fromString(memberId), memberUpdateReqDto, profileImage);

        return JsonResponse.ok("개인 정보가 성공적으로 수정되었습니다.");
    }

    /**
     * 회원 탈퇴
     */
    @DeleteMapping
    public ResponseEntity<ResponseWrapper<Nullable>> deleteMember(@AuthenticationPrincipal String memberId) {

        memberService.deleteMember(UUID.fromString(memberId));

        return JsonResponse.ok("회원이 성공적으로 삭제되었습니다.");
    }

    /**
     * 통신 테스트를 위한 API
     */
    @GetMapping("/test")
    public ResponseEntity<String> testNotificationService() {

        String testResponse = notificationServiceClient.testNotificationService();

        return ResponseEntity.ok("notification-service에서 받은 응답 : " + testResponse);
    }

}
