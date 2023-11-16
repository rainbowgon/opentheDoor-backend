package com.rainbowgon.reservationservice.domain.reservation.controller;

import com.rainbowgon.reservationservice.domain.reservation.dto.request.ReservationReqDto;
import com.rainbowgon.reservationservice.domain.reservation.dto.response.*;
import com.rainbowgon.reservationservice.domain.reservation.service.ReservationService;
import com.rainbowgon.reservationservice.domain.waiting.dto.request.WaitingReqDto;
import com.rainbowgon.reservationservice.domain.waiting.service.WaitingService;
import com.rainbowgon.reservationservice.global.response.JsonResponse;
import com.rainbowgon.reservationservice.global.response.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
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

    @PostMapping("/waiting")
    public ResponseEntity<ResponseWrapper<Nullable>> waitEmptyTimeSlot(
            @RequestHeader String memberId, @RequestBody WaitingReqDto waitingReqDto) {

        waitingService.waitEmptyTimeSlot(memberId, waitingReqDto);

        return JsonResponse.ok("예약 대기 신청을 완료했습니다.");
    }

    @PostMapping("/waiting/cancel")
    public ResponseEntity<ResponseWrapper<Nullable>> cancelWaiting(
            @RequestHeader String memberId, @RequestBody WaitingReqDto waitingReqDto) {

        waitingService.cancelWaiting(memberId, waitingReqDto);

        return JsonResponse.ok("예약 대기 신청을 취소했습니다.");
    }
}
