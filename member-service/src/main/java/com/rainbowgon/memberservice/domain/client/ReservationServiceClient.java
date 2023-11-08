package com.rainbowgon.memberservice.domain.client;

import com.rainbowgon.memberservice.domain.client.dto.input.ReservationInDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "reservation-service")
public interface ReservationServiceClient {

    /**
     * 회원의 예약 내역 가져오기 (홈 화면)
     */
    @GetMapping("/clients/reservations/{profile-id}")
    List<ReservationInDto> getMemberReservationList(@PathVariable("profile-id") Long profileId);

}
