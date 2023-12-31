package com.rainbowgon.notificationservice.domain.notification.controller;


import com.rainbowgon.notificationservice.domain.notification.service.NotificationService;
import com.rainbowgon.notificationservice.global.client.dto.input.BookmarkInDto;
import com.rainbowgon.notificationservice.global.client.dto.input.ReservationInDto;
import com.rainbowgon.notificationservice.global.client.dto.input.WaitingInDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients/notifications")
@RequiredArgsConstructor
public class NotificationClientController {

    private final NotificationService notificationService;

    @GetMapping("/test")
    public ResponseEntity<String> test() {

        return ResponseEntity.ok("알림 서비스 통신 테스트 성공");
    }

    @PostMapping("/bookmark")
    public ResponseEntity<String> sendBookmark(
            @RequestBody List<BookmarkInDto> bookmarkInDtoList) {

        notificationService.sendBookmarkMessage(bookmarkInDtoList);
        return ResponseEntity.ok("북마크한 테마 예약 오픈 알림 리스트를 성공적으로 전송했습니다.");
    }

    @PostMapping("/reservation")
    public ResponseEntity<String> sendReservation(
            @RequestBody ReservationInDto reservationInDto) {

        notificationService.sendReservationMessage(reservationInDto);
        return ResponseEntity.ok("예약 확정 및 취소 알림 리스트를 성공적으로 전송했습니다.");
    }

    @PostMapping("/waiting")
    public ResponseEntity<String> sendWaiting(
            @RequestBody List<WaitingInDto> waitingInDtoList) {

        notificationService.sendWaitingMessage(waitingInDtoList);
        return ResponseEntity.ok("예약 대기 알림 리스트를 성공적으로 전송했습니다.");
    }
}