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
import com.rainbowgon.memberservice.global.jwt.JwtTokenDto;
import com.rainbowgon.memberservice.global.redis.service.BookmarkRedisService;
import com.rainbowgon.memberservice.global.redis.service.SortingRedisService;
import com.rainbowgon.memberservice.global.redis.service.TokenRedisService;
import com.rainbowgon.memberservice.global.response.JsonResponse;
import com.rainbowgon.memberservice.global.response.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
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

    private final BookmarkRedisService bookmarkRedisService;
    private final SortingRedisService sortingRedisService;
    private final TokenRedisService tokenRedisService;


    /**
     * TODO 카카오 로그인
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

        return JsonResponse.ok("인증번호를 발송했습니다.", "인증번호 = " + checkNumber);
    }

    /**
     * 개인 정보 조회
     * 개인 정보 수정 요청 시 보내줄 데이터
     */
    @GetMapping("/me")
    public ResponseEntity<ResponseWrapper<MemberInfoResDto>> selectMemberInfo(
            @RequestHeader("memberId") String memberId) {

        log.info("[MemberController] selectMemberInfo ... memberId = {}", memberId);
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
            @RequestHeader("memberId") String memberId,
            @RequestPart(value = "info") MemberUpdateReqDto memberUpdateReqDto,
            @RequestPart(value = "file", required = false) MultipartFile profileImage) {

        memberService.updateMemberInfo(UUID.fromString(memberId), memberUpdateReqDto, profileImage);

        return JsonResponse.ok("개인 정보가 성공적으로 수정되었습니다.");
    }

    /**
     * 회원 탈퇴
     */
    @DeleteMapping
    public ResponseEntity<ResponseWrapper<Nullable>> deleteMember(@RequestHeader("memberId") String memberId) {

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

    /**
     * redis server 조회를 위한 API
     */
    @GetMapping("/redis")
    public ResponseEntity<String> selectAllRedis() {

        Integer bookmarkKeys = bookmarkRedisService.keysAll();
        Integer sortingKeys = sortingRedisService.keysAll();
        Integer tokenKeys = tokenRedisService.keysAll();

        StringBuilder sb = new StringBuilder();
        sb.append("bookmark redis keys : ").append(bookmarkKeys).append("\n");
        sb.append("sorting redis keys : ").append(sortingKeys).append("\n");
        sb.append("token redis keys : ").append(tokenKeys).append("\n");

        return ResponseEntity.ok(sb.toString());
    }

    /**
     * redis server 삭제를 위한 API
     */
    @DeleteMapping("/redis")
    public ResponseEntity<String> deleteAllRedis() {

        Boolean isBookmarkRedisDeleted = bookmarkRedisService.flushAll();
        Boolean isSortingRedisDeleted = sortingRedisService.flushAll();
        Boolean isTokenRedisDeleted = tokenRedisService.flushAll();

        StringBuilder sb = new StringBuilder();
        sb.append("bookmark redis deleted : ").append(isBookmarkRedisDeleted).append("\n");
        sb.append("sorting redis deleted : ").append(isSortingRedisDeleted).append("\n");
        sb.append("token redis deleted : ").append(isTokenRedisDeleted).append("\n");

        return ResponseEntity.ok(sb.toString());
    }

}
