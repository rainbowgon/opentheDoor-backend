package com.rainbowgon.memberservice.domain.oauth.controller;

import com.rainbowgon.memberservice.domain.member.dto.MemberDto;
import com.rainbowgon.memberservice.domain.member.service.MemberService;
import com.rainbowgon.memberservice.domain.oauth.dto.KakaoUserInfoDto;
import com.rainbowgon.memberservice.domain.oauth.dto.response.OauthSignUpResDto;
import com.rainbowgon.memberservice.domain.oauth.service.KakaoLoginService;
import com.rainbowgon.memberservice.global.jwt.JwtTokenDto;
import com.rainbowgon.memberservice.global.response.JsonResponse;
import com.rainbowgon.memberservice.global.response.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

    private final KakaoLoginService kakaoLoginService;
    private final MemberService memberService;


    /**
     * 인가 코드 값으로 사용자 정보 가져오기
     */
    @GetMapping("/kakao/callback")
    public ResponseEntity<?> kakaoCallback(@RequestParam("code") String code) throws Exception {

        // 인가코드(code)로 토큰 발급 요청
        String kakaoAccessToken = kakaoLoginService.getToken(code);

        // accessToken으로 사용자 정보 가져오기
        KakaoUserInfoDto kakaoUserInfoDto = kakaoLoginService.getProfile(kakaoAccessToken);

        // 가입된 회원인지 확인
        MemberDto member = memberService.findMemberByProviderId(kakaoUserInfoDto.getId());

        // 가입 정보가 없다면 회원가입 요청 (kakao id, profile image, nickname 반환)
        if (member == null) {
            return JsonResponse.ok("카카오 프로필 정보를 성공적으로 가져왔습니다.", OauthSignUpResDto.fromKakao(kakaoUserInfoDto));
        }

        // 가입 정보가 있다면 로그인 요청 (profileId 반환)
        return JsonResponse.ok("로그인 진행을 위한 fcm token 요청", member.getProfileId());
    }

    /**
     * 카카오 로그인
     */
    @PostMapping("/login/kakao")
    public ResponseEntity<ResponseWrapper<JwtTokenDto>> kakaoLogin(
            @RequestParam("fcmToken") String fcmToken,
            @RequestParam("profileId") Long profileId) {

        JwtTokenDto jwtTokenDto = kakaoLoginService.kakaoLogin(fcmToken, profileId);

        return JsonResponse.ok("로그인에 성공하였습니다.", jwtTokenDto);
    }
}
