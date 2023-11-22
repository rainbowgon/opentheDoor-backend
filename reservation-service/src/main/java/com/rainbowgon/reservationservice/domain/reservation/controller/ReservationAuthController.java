package com.rainbowgon.reservationservice.domain.reservation.controller;

import com.rainbowgon.reservationservice.domain.reservation.dto.request.ReservationReqDto;
import com.rainbowgon.reservationservice.domain.reservation.dto.response.*;
import com.rainbowgon.reservationservice.domain.reservation.service.ReservationService;
import com.rainbowgon.reservationservice.domain.waiting.service.WaitingService;
import com.rainbowgon.reservationservice.global.response.JsonResponse;
import com.rainbowgon.reservationservice.global.response.ResponseWrapper;
import com.rainbowgon.reservationservice.global.utils.DateTimeValidator;
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
    private final WaitingService waitingService;

    @GetMapping("/{theme-id}")
    public ResponseEntity<ResponseWrapper<ReservationBaseInfoResDto>> getReservationBaseInfo(
            @RequestHeader String memberId, @PathVariable("theme-id") String themeId) {

        ReservationBaseInfoResDto reservationBaseInfoResDto =
                reservationService.getReservationBaseInfo(memberId, themeId);

        return JsonResponse.ok("회원의 테마 예약 페이지 정보를 가져왔습니다.", reservationBaseInfoResDto);
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<ReservationResultResDto>> makeReservation(
            @RequestHeader String memberId, @RequestBody ReservationReqDto reservationReqDto) {

        System.out.println("=======================================================");
        System.out.println("reservationReqDto = " + reservationReqDto);
        System.out.println("=======================================================");

        validateDateTime(reservationReqDto);

        ReservationResultResDto reservationResultResDto =
                reservationService.makeReservation(memberId, reservationReqDto);

        if (reservationResultResDto.getIsSucceed().equals(ReservationSuccess.FAIL)) {
            return JsonResponse.of(HttpStatus.CONFLICT, "예약에 실패했습니다.", reservationResultResDto);
        }

        return JsonResponse.ok("성공적으로 예약되었습니다.", reservationResultResDto);
    }

    @GetMapping("/reserved")
    public ResponseEntity<ResponseWrapper<List<ReservationBriefResDto>>> getAllReservationHistory(
            @RequestHeader String memberId) {

        List<ReservationBriefResDto> historyList = reservationService.getAllReservationHistory(memberId);

        return JsonResponse.ok("회원의 예약 내역 리스트를 가져왔습니다.", historyList);
    }

    @GetMapping("/reserved/{reservation-id}")
    public ResponseEntity<ResponseWrapper<ReservationDetailResDto>> getReservationDetail(
            @RequestHeader String memberId, @PathVariable("reservation-id") Long reservationId) {

        ReservationDetailResDto reservationDetailResDto =
                reservationService.getReservationDetail(memberId, reservationId);

        return JsonResponse.ok("회원의 예약 상세 정보를 가져왔습니다.", reservationDetailResDto);
    }

    private void validateDateTime(ReservationReqDto reservationReqDto) {

        DateTimeValidator.validateDate(reservationReqDto.getTargetDate());
        DateTimeValidator.validateTime(reservationReqDto.getTargetTime());
    }

}
