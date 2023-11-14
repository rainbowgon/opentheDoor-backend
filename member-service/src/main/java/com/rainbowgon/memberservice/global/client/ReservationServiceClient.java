package com.rainbowgon.memberservice.global.client;

import com.rainbowgon.memberservice.global.client.dto.input.ReservationInDto;
import com.rainbowgon.memberservice.global.client.dto.output.ReservationOutDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "reservation-service")
public interface ReservationServiceClient {

    /**
     * 회원의 예약 정보 가져오기
     * - 회원 ID와 테마 ID로 예약 ID 조회
     */
    @GetMapping("/clients/reservations")
    ReservationInDto getMemberReservationInfo(@RequestBody ReservationOutDto reservationOutDto);

}
