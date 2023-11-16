package com.rainbowgon.memberservice.domain.member.controller;

import com.rainbowgon.memberservice.domain.member.dto.response.BookerInfoResDto;
import com.rainbowgon.memberservice.domain.member.service.MemberService;
import com.rainbowgon.memberservice.global.client.dto.input.MemberIdListInDto;
import com.rainbowgon.memberservice.global.client.dto.output.FcmTokenListOutDto;
import com.rainbowgon.memberservice.global.client.dto.output.FcmTokenOutDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/clients/members")
public class MemberClientController {

    private final MemberService memberService;


    /**
     * memberId로 회원 이름과 전화번호 조회
     */
    @GetMapping("/booker/{member-id}")
    public ResponseEntity<BookerInfoResDto> selectBookerInfo(@PathVariable("member-id") String memberId) {

        BookerInfoResDto bookerInfo = memberService.selectBookerInfo(memberId);

        return ResponseEntity.ok(bookerInfo);
    }

    /**
     * memberId로 fcm token 조회
     */
    @GetMapping("/fcm/{member-id}")
    public ResponseEntity<FcmTokenOutDto> selectMemberFcmToken(@PathVariable("member-id") String memberId) {

        FcmTokenOutDto MemberFcmToken = memberService.selectMemberFcmToken(memberId);

        return ResponseEntity.ok(MemberFcmToken);
    }

    /**
     * memberIdList -> fcmTokenList
     */
    @PostMapping("/fcm")
    public ResponseEntity<FcmTokenListOutDto> selectMemberFcmTokenList(@RequestBody MemberIdListInDto memberIdList) {

        FcmTokenListOutDto fcmTokenList = memberService.selectMemberFcmTokenList(memberIdList.getMemberIdList());

        return ResponseEntity.ok(fcmTokenList);
    }

}
