package com.rainbowgon.member.domain.profile.controller;

import com.rainbowgon.member.domain.profile.entity.NotificationStatus;
import com.rainbowgon.member.domain.profile.service.ProfileService;
import com.rainbowgon.member.global.response.JsonResponse;
import com.rainbowgon.member.global.response.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
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
            @AuthenticationPrincipal String memberId) {

        NotificationStatus status = profileService.updateNotificationStatus(UUID.fromString(memberId));

        return JsonResponse.ok("알림 설정이 변경되었습니다.", status);
    }

}
