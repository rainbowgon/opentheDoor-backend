package com.rainbowgon.reservationservice.domain.reservation.controller;

import com.rainbowgon.reservationservice.domain.reservation.dto.request.ReservationReqDto;
import com.rainbowgon.reservationservice.domain.reservation.dto.response.ReservationBaseInfoResDto;
import com.rainbowgon.reservationservice.domain.reservation.service.ReservationService;
import com.rainbowgon.reservationservice.global.response.JsonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations/auth")
public class ReservationAuthController {

    private final ReservationService reservationService;

    @GetMapping("/{theme-id}")
    public ResponseEntity<?> getReservationBaseInfo(
            @RequestHeader String memberId, @PathVariable("theme-id") String themeId) {

        ReservationBaseInfoResDto reservationBaseInfoResDto =
                reservationService.getReservationBaseInfo(memberId, themeId);

        return JsonResponse.ok("회원의 테마 예약 페이지 정보를 가져왔습니다.", reservationBaseInfoResDto);
    }

    @PostMapping("/{theme-id}")
    public ResponseEntity<?> makeReservation(
            @RequestHeader String memberId, @RequestBody ReservationReqDto reservationReqDto) {

    }
}
