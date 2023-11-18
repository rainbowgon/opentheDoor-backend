package com.rainbowgon.memberservice.domain.oauth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rainbowgon.memberservice.domain.member.dto.MemberDto;
import com.rainbowgon.memberservice.domain.oauth.dto.KakaoTokenDto;
import com.rainbowgon.memberservice.domain.oauth.dto.KakaoUserInfoDto;
import com.rainbowgon.memberservice.domain.profile.service.ProfileService;
import com.rainbowgon.memberservice.global.error.exception.AuthKakaoProfileFailureException;
import com.rainbowgon.memberservice.global.error.exception.AuthKakaoTokenFailureException;
import com.rainbowgon.memberservice.global.jwt.JwtTokenDto;
import com.rainbowgon.memberservice.global.jwt.JwtTokenProvider;
import com.rainbowgon.memberservice.global.redis.dto.Token;
import com.rainbowgon.memberservice.global.redis.repository.TokenRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.time.Duration;


@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoLoginService {

    @Value("${spring.oauth.kakao.client-id}")
    private String kakaoClientId;

    @Value("${spring.oauth.kakao.redirect-uri}")
    private String kakaoRedirectUri;

    private final ObjectMapper objectMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRedisRepository tokenRedisRepository;
    private final ProfileService profileService;

    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 7일


    /**
     * 백엔드 -> 카카오
     * 인가 코드로 카카오 토큰 받기
     */
    public String getToken(String code) throws Exception {

        log.info("[KakaoLoginService] getToken ... code = {}", code);

        // 요청 URL
        String kakaoTokenUri = "https://kauth.kakao.com/oauth/token";

        // request body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", kakaoClientId);
        body.add("redirect_uri", kakaoRedirectUri);
        body.add("code", code);

        log.info("[KakaoLoginService] getToken ... 카카오에 토큰 요청 직전");

        // 카카오에 token 요청
        String token = WebClient.create()
                .post()
                .uri(kakaoTokenUri)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(body))
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofMillis(5000000))
                .blockOptional().orElseThrow(AuthKakaoTokenFailureException::new);
        
        log.info("[KakaoLoginService] getToken ... 카카오에 토큰 요청 직후");

        // 객체로 전환
        KakaoTokenDto kakaoTokenDto = objectMapper.readValue(token, KakaoTokenDto.class);
        return kakaoTokenDto.getAccessToken();
    }

    /**
     * 백엔드 -> 카카오
     * 카카오 토큰으로 사용자 정보 가져오기
     */
    public KakaoUserInfoDto getProfile(String kakaoAccessToken) throws Exception {

        // URL
        String kakaoUserInfoUrl = "https://kapi.kakao.com/v2/user/me";

        String profile = WebClient.create()
                .post()
                .uri(kakaoUserInfoUrl)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("Authorization", "Bearer " + kakaoAccessToken)
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofMillis(500000))
                .blockOptional().orElseThrow(AuthKakaoProfileFailureException::new);

        return objectMapper.readValue(profile, KakaoUserInfoDto.class);
    }

    /**
     * 카카오로 로그인
     */
    @Transactional
    public JwtTokenDto kakaoLogin(String fcmToken, Long profileId) {

        // 유효한 프로필 ID인지 확인
        MemberDto memberDto = profileService.findProfileById(profileId);

        // accessToken, refreshToken 생성
        String accessToken = jwtTokenProvider.generateAccessToken(memberDto.getProfileId());
        String refreshToken = jwtTokenProvider.generateRefreshToken(memberDto.getProfileId());

        // token redis 업데이트
        Token tokenDto = tokenRedisRepository.save(
                Token.builder()
                        .profileId(memberDto.getProfileId())
                        .memberId(memberDto.getMemberId())
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .fcmToken(fcmToken)
                        .expiration(REFRESH_TOKEN_EXPIRE_TIME)
                        .build());

        return JwtTokenDto.of(tokenDto.getAccessToken(), tokenDto.getRefreshToken());

    }

}
