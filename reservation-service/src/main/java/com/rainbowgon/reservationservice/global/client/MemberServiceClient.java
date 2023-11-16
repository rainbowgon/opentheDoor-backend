package com.rainbowgon.reservationservice.global.client;

import com.rainbowgon.reservationservice.global.client.dto.input.FcmTokenInDto;
import com.rainbowgon.reservationservice.global.client.dto.input.MemberBriefInfoInDto;
import com.rainbowgon.reservationservice.global.client.dto.output.MemberIdListOutDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "member-service")
@RequestMapping("/clients/members")
public interface MemberServiceClient {

    @GetMapping("/booker/{member-id}")
    MemberBriefInfoInDto getMemberBriefInfo(@PathVariable("member-id") String memberId);

    @GetMapping("/fcm/{member-id}")
    FcmTokenInDto getFcmToken(@PathVariable("member-id") String memberId);

    @PostMapping("/fcm")
    List<FcmTokenInDto> getFcmTokenList(@RequestBody MemberIdListOutDto memberIdListOutDto);
}
