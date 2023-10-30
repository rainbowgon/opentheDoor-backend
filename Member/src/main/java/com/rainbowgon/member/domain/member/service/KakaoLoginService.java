package com.rainbowgon.member.domain.member.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rainbowgon.member.domain.member.dto.response.oauth.KakaoProfileResDto;
import com.rainbowgon.member.domain.member.dto.response.oauth.KakaoTokenResDto;
import com.rainbowgon.member.domain.member.repository.MemberRepository;
import com.rainbowgon.member.global.error.exception.AuthKakaoProfileFailureException;
import com.rainbowgon.member.global.error.exception.AuthKakaoTokenFailureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;


@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoLoginService {

    private final MemberRepository memberRepository;

    @Value("${oauth.kakao.client-id}")
    private String kakaoClientId;

    @Value("${oauth.kakao.redirect-uri}")
    private String kakaoRedirectUri;

    // json 타입을 객체로 변환하기 위한 객체
    @Autowired
    private ObjectMapper objectMapper;

    // code를 이용해 kakaoToken 가져오기
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
        KakaoTokenResDto kakaoTokenResDto = objectMapper.readValue(token, KakaoTokenResDto.class);
        return kakaoTokenResDto.getAccessToken();
    }

    // kakao access_token으로 사용자 정보 가져오기
    public KakaoProfileResDto getProfile(String kakaoAccessToken) throws Exception {

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

        return objectMapper.readValue(profile, KakaoProfileResDto.class);
    }
}
