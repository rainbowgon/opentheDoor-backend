package com.rainbowgon.notificationservice.domain.notification.controller;


import com.rainbowgon.notificationservice.domain.notification.client.dto.in.BookmarkInDto;
import com.rainbowgon.notificationservice.domain.notification.client.dto.in.ReservationInDto;
import com.rainbowgon.notificationservice.domain.notification.client.dto.in.WaitingInDto;
import com.rainbowgon.notificationservice.domain.notification.dto.response.NotificationListResDto;
import com.rainbowgon.notificationservice.domain.notification.service.NotificationService;
import com.rainbowgon.notificationservice.global.response.JsonResponse;
import com.rainbowgon.notificationservice.global.response.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<NotificationListResDto>>> selectNotificationList() {

        Long profileId = 1L;
        List<NotificationListResDto> notificationList = notificationService.selectNotificationList(profileId);
        return JsonResponse.ok("회원 전체 알림 리스트를 성공적으로가져왔습니다.", notificationList);
    }

    @PatchMapping("/{notification-id}")
    public ResponseEntity<ResponseWrapper<Nullable>> checkOneNotification(
            @PathVariable(name = "notification-id") Long notificationId) {

        notificationService.checkOneNotification(notificationId);
        return JsonResponse.ok("알림 한 개를 확인 완료로 변경했습니다.");
    }

    @PatchMapping
    public ResponseEntity<ResponseWrapper<Nullable>> checkAllNotification() {

        Long profileId = 1L;
        notificationService.checkAllNotification(profileId);
        return JsonResponse.ok("알림 전체를 확인 완료로 변경했습니다.");
    }


    @PostMapping("/bookmark")
    public ResponseEntity<ResponseWrapper<Nullable>> sendBookmark(
            @RequestBody List<BookmarkInDto> bookmarkInDtoList) {

        notificationService.makeBookmarkMessage(bookmarkInDtoList);
        return JsonResponse.ok("북마크한 테마 예약 오픈 알림 리스트를 성공적으로 전송했습니다.");
    }

    @PostMapping("/reservation")
    public ResponseEntity<ResponseWrapper<Nullable>> sendReservation(
            @RequestBody List<ReservationInDto> reservationInDtoList) {

        notificationService.makeReservationMessage(reservationInDtoList);
        return JsonResponse.ok("예약 확정 및 취소 알림 리스트를 성공적으로 전송했습니다.");
    }

    @PostMapping("/waiting")
    public ResponseEntity<ResponseWrapper<Nullable>> sendWaiting(
            @RequestBody List<WaitingInDto> waitingInDtoList) {

        notificationService.makeWaitingMessage(waitingInDtoList);
        return JsonResponse.ok("예약 대기 알림 리스트를 성공적으로 전송했습니다.");
    }
}