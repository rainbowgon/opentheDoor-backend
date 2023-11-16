package com.rainbowgon.reservationservice.global.client;

import com.rainbowgon.reservationservice.global.client.dto.input.FcmTokenInDto;
import com.rainbowgon.reservationservice.global.client.dto.input.MemberBriefInfoInDto;
import com.rainbowgon.reservationservice.global.client.dto.output.MemberIdOutDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "member-service")
@RequestMapping("/clients/members")
public interface MemberServiceClient {

    @GetMapping("/booker/{member-id}")
    MemberBriefInfoInDto getMemberBriefInfo(@PathVariable("member-id") String memberId);

    @GetMapping("/fcm")
    FcmTokenInDto getFcmToken(@RequestBody MemberIdOutDto memberIdOutDto);

    @GetMapping("/fcm/list")
    List<FcmTokenInDto> getFcmTokenList(@RequestBody List<MemberIdOutDto> memberIdOutDtoList);
}
