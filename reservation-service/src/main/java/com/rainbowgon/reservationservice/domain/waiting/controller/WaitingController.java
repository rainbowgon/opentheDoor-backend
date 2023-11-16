package com.rainbowgon.reservationservice.domain.waiting.controller;

import com.rainbowgon.reservationservice.domain.waiting.dto.request.EmptyTimeSlotReqDto;
import com.rainbowgon.reservationservice.domain.waiting.service.WaitingService;
import com.rainbowgon.reservationservice.global.response.JsonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/waiting/unauth")
public class WaitingController {

    private final WaitingService waitingService;

    @PostMapping("/notify/empty-slot")
    public ResponseEntity<?> alertEmptyTimeSlot(@RequestBody EmptyTimeSlotReqDto emptyTimeSlotReqDto) {

        waitingService.notifyEmptyTimeSlot(emptyTimeSlotReqDto);

        return JsonResponse.ok("예약 대기한 사용자에게 빈 자리 알림을 보냈습니다.");
    }
}
