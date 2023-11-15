package com.rainbowgon.reservationservice.global.client;

import com.rainbowgon.reservationservice.global.client.dto.input.MemberBriefInfoInDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "member-service")
@RequestMapping("/clients/members")
public interface MemberServiceClient {

    @GetMapping("/booker/{member-id}")
    MemberBriefInfoInDto getMemberBriefInfo(@PathVariable("member-id") String memberId);

    @GetMapping("/fcm/{member-id}")
    String getFcmToken(@PathVariable("member-id") String memberId);
}
