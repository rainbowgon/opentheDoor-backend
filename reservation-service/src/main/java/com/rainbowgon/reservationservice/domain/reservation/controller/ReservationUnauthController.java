package com.rainbowgon.reservationservice.domain.reservation.controller;

import com.rainbowgon.reservationservice.domain.reservation.dto.request.ReservationReqDto;
import com.rainbowgon.reservationservice.domain.reservation.dto.request.UnauthReservationDetailReqDto;
import com.rainbowgon.reservationservice.domain.reservation.dto.response.ReservationBaseInfoResDto;
import com.rainbowgon.reservationservice.domain.reservation.dto.response.ReservationDetailResDto;
import com.rainbowgon.reservationservice.domain.reservation.dto.response.ReservationResultResDto;
import com.rainbowgon.reservationservice.domain.reservation.dto.response.ReservationSuccess;
import com.rainbowgon.reservationservice.domain.reservation.service.ReservationService;
import com.rainbowgon.reservationservice.global.response.JsonResponse;
import com.rainbowgon.reservationservice.global.response.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations/unauth")
public class ReservationUnauthController {

    private final ReservationService reservationService;

    @GetMapping("/{theme-id}")
    public ResponseEntity<ResponseWrapper<ReservationBaseInfoResDto>> getReservationBaseInfo(
            @PathVariable("theme-id") String themeId) {

        ReservationBaseInfoResDto reservationBaseInfoResDto =
                reservationService.getReservationBaseInfo(themeId);

        return JsonResponse.ok("비회원의 테마 예약 페이지 정보를 가져왔습니다.", reservationBaseInfoResDto);
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<ReservationResultResDto>> makeReservation(
            @RequestBody ReservationReqDto reservationReqDto) {

        ReservationResultResDto reservationResultResDto =
                reservationService.makeReservation(reservationReqDto);

        if (reservationResultResDto.getIsSucceed().equals(ReservationSuccess.FAIL)) {
            return JsonResponse.of(HttpStatus.CONFLICT, "예약에 실패했습니다.", reservationResultResDto);
        }

        return JsonResponse.ok("성공적으로 예약되었습니다.", reservationResultResDto);
    }

    @PostMapping("/reserved")
    public ResponseEntity<ResponseWrapper<ReservationDetailResDto>> getReservationDetail(
            @RequestBody UnauthReservationDetailReqDto unauthReservationDetailReqDto) {
        ReservationDetailResDto reservationDetailResDto =
                reservationService.getReservationDetail(unauthReservationDetailReqDto);

        return JsonResponse.ok("비회원의 예약 상세 정보를 가져왔습니다.", reservationDetailResDto);
    }
}
