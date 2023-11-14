package com.rainbowgon.memberservice.domain.member.controller;

import com.rainbowgon.memberservice.domain.member.dto.response.BookerInfoResDto;
import com.rainbowgon.memberservice.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
