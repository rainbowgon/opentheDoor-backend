package com.rainbowgon.reservationservice.global.client;

import com.rainbowgon.reservationservice.global.client.dto.input.FcmTokenInDto;
import com.rainbowgon.reservationservice.global.client.dto.input.MemberBriefInfoInDto;
import com.rainbowgon.reservationservice.global.client.dto.output.MemberIdListOutDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "member-service", path = "/clients/members")
public interface MemberServiceClient {

    @GetMapping("/booker/{member-id}")
    MemberBriefInfoInDto getMemberBriefInfo(@PathVariable("member-id") String memberId);

    @GetMapping("/fcm/{member-id}")
    FcmTokenInDto getFcmToken(@PathVariable("member-id") String memberId);

    @PostMapping("/fcm")
    List<FcmTokenInDto> getFcmTokenList(@RequestBody MemberIdListOutDto memberIdListOutDto);
}
