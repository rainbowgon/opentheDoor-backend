package com.rainbowgon.reservationservice.domain.reservation.controller;

import com.rainbowgon.reservationservice.domain.reservation.dto.request.ReservationReqDto;
import com.rainbowgon.reservationservice.domain.reservation.dto.response.ReservationBaseInfoResDto;
import com.rainbowgon.reservationservice.domain.reservation.dto.response.ReservationBriefResDto;
import com.rainbowgon.reservationservice.domain.reservation.dto.response.ReservationResultResDto;
import com.rainbowgon.reservationservice.domain.reservation.dto.response.ReservationSuccess;
import com.rainbowgon.reservationservice.domain.reservation.service.ReservationService;
import com.rainbowgon.reservationservice.global.response.JsonResponse;
import com.rainbowgon.reservationservice.global.response.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<ResponseWrapper<ReservationResultResDto>> makeReservation(
            @RequestHeader String memberId, @RequestBody ReservationReqDto reservationReqDto) {

        ReservationResultResDto reservationResultResDto =
                reservationService.makeReservation(memberId, reservationReqDto);

        if (reservationResultResDto.getIsSucceed().equals(ReservationSuccess.FAIL)) {
            return JsonResponse.of(HttpStatus.CONFLICT, "예약에 실패했습니다.", reservationResultResDto);
        }

        return JsonResponse.ok("성공적으로 예약되었습니다.", reservationResultResDto);
    }

    @GetMapping("/reserved")
    public ResponseEntity<?> getAllReservationHistory(@RequestHeader String memberId) {
        List<ReservationBriefResDto> historyList = reservationService.getAllReservationHistory(memberId);
    }
}
