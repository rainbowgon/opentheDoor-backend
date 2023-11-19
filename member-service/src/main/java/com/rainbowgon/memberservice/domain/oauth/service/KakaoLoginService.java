package com.rainbowgon.memberservice.domain.oauth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rainbowgon.memberservice.domain.member.dto.MemberDto;
import com.rainbowgon.memberservice.domain.member.dto.response.LoginResDto;
import com.rainbowgon.memberservice.domain.oauth.dto.KakaoTokenDto;
import com.rainbowgon.memberservice.domain.oauth.dto.KakaoUserInfoDto;
import com.rainbowgon.memberservice.domain.profile.service.ProfileService;
import com.rainbowgon.memberservice.global.error.exception.AuthKakaoProfileFailureException;
import com.rainbowgon.memberservice.global.error.exception.AuthKakaoTokenFailureException;
import com.rainbowgon.memberservice.global.jwt.JwtTokenProvider;
import com.rainbowgon.memberservice.global.redis.dto.Token;
import com.rainbowgon.memberservice.global.redis.repository.TokenRedisRepository;
import com.rainbowgon.memberservice.global.s3.S3FileService;
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
import java.io.IOException;
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
    private final S3FileService s3FileService;

    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 7일
    private static final String S3_PATH = "profile-image";


    /**
     * 백엔드 -> 카카오
     * 인가 코드로 카카오 토큰 받기
     */
    public String getToken(String code) throws Exception {

        // 요청 URL
        String kakaoTokenUri = "https://kauth.kakao.com/oauth/token";

        // request body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", kakaoClientId);
        body.add("redirect_uri", kakaoRedirectUri);
        body.add("code", code);

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
     * 카카오에서 가져온 프로필 이미지 url -> s3 url 변환
     */
    public String getProfileImageUrl(String kakaoUrl, String kakaoNickname) throws IOException {
        String fileName = s3FileService.saveFile(S3_PATH, kakaoUrl, kakaoNickname);
        return s3FileService.getS3Url(fileName);
    }

    /**
     * 카카오로 로그인
     */
    @Transactional
    public LoginResDto kakaoLogin(String fcmToken, Long profileId) {

        // 유효한 프로필 ID인지 확인
        MemberDto memberDto = profileService.selectProfileById(profileId);

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

        // 프로필 image s3 url 가져오기
        String profileImageUrl = s3FileService.getS3Url(memberDto.getProfileImage());

        return LoginResDto.of(
                tokenDto.getAccessToken(), tokenDto.getRefreshToken(), memberDto.getNickname(), profileImageUrl);
    }

}
