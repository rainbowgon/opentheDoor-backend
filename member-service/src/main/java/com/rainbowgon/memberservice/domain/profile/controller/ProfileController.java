package com.rainbowgon.memberservice.domain.profile.controller;

import com.rainbowgon.memberservice.domain.profile.service.ProfileService;
import com.rainbowgon.memberservice.global.entity.NotificationStatus;
import com.rainbowgon.memberservice.global.response.JsonResponse;
import com.rainbowgon.memberservice.global.response.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileService profileService;

    /**
     * 앱 내 모든 알림 on/off
     */
    @PatchMapping("/notifications")
    public ResponseEntity<ResponseWrapper<NotificationStatus>> updateNotificationStatus(
            @RequestHeader("memberId") String memberId) {

        NotificationStatus status = profileService.updateNotificationStatus(UUID.fromString(memberId));

        return JsonResponse.ok("전체 알림 설정이 변경되었습니다.", status);
    }

    /**
     * 북마크 시, 자동으로 예약 오픈 알림 on/off
     */
    @PatchMapping("/notifications/bookmarks")
    public ResponseEntity<ResponseWrapper<NotificationStatus>> updateBookmarkNotificationStatus(
            @RequestHeader("memberId") String memberId) {

        NotificationStatus status = profileService.updateBookmarkNotificationStatus(UUID.fromString(memberId));

        return JsonResponse.ok("북마크 알림 설정이 변경되었습니다.", status);
    }

}
