package com.rainbowgon.notificationservice.domain.notification.controller;


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

        Long profileId = 3L;
        List<NotificationListResDto> notificationList = notificationService.selectNotificationList(profileId);
        return JsonResponse.ok("회원 전체 알림 리스트를 성공적으로 가져왔습니다.", notificationList);
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

}