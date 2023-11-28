package com.rainbowgon.reservationservice.domain.waiting.controller;

import com.rainbowgon.reservationservice.domain.waiting.dto.request.WaitingReqDto;
import com.rainbowgon.reservationservice.domain.waiting.service.WaitingService;
import com.rainbowgon.reservationservice.global.response.JsonResponse;
import com.rainbowgon.reservationservice.global.response.ResponseWrapper;
import com.rainbowgon.reservationservice.global.utils.DateTimeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/waitings/auth")
public class WaitingAuthController {

    private final WaitingService waitingService;

    @PostMapping
    public ResponseEntity<ResponseWrapper<Nullable>> waitEmptyTimeSlot(
            @RequestHeader String memberId, @RequestBody WaitingReqDto waitingReqDto) {

        validateDateTime(waitingReqDto);

        waitingService.waitEmptyTimeSlot(memberId, waitingReqDto);

        return JsonResponse.ok("예약 대기 신청을 완료했습니다.");
    }

    @PostMapping("/cancel")
    public ResponseEntity<ResponseWrapper<Nullable>> cancelWaiting(
            @RequestHeader String memberId, @RequestBody WaitingReqDto waitingReqDto) {

        validateDateTime(waitingReqDto);

        waitingService.cancelWaiting(memberId, waitingReqDto);

        return JsonResponse.ok("예약 대기 신청을 취소했습니다.");
    }

    private void validateDateTime(WaitingReqDto waitingReqDto) {

        DateTimeValidator.validateDate(waitingReqDto.getTargetDate());
        DateTimeValidator.validateTime(waitingReqDto.getTargetTime());
    }
}
