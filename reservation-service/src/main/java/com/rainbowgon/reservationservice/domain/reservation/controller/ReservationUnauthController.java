package com.rainbowgon.reservationservice.domain.reservation.controller;

import com.rainbowgon.reservationservice.domain.reservation.dto.response.ReservationBaseInfoResDto;
import com.rainbowgon.reservationservice.domain.reservation.service.ReservationService;
import com.rainbowgon.reservationservice.global.response.JsonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations/unauth")
public class ReservationUnauthController {

    private final ReservationService reservationService;

    @GetMapping("/{theme-id}")
    public ResponseEntity<?> getReservationBaseInfo(@PathVariable("theme-id") String themeId) {

        ReservationBaseInfoResDto reservationBaseInfoResDto =
                reservationService.getReservationBaseInfo(themeId);

        return JsonResponse.ok("비회원의 테마 예약 페이지 정보를 가져왔습니다.", reservationBaseInfoResDto);
    }
}
