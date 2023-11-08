package com.rainbowgon.memberservice.domain.member.controller;

import com.rainbowgon.memberservice.domain.client.NotificationServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/clients/members")
public class MemberClientController {

    private final NotificationServiceClient notificationServiceClient;

    /**
     * 통신 테스트를 위한 API
     */
    @GetMapping("/test")
    public ResponseEntity<String> testNotificationService() {

        String testResponse = notificationServiceClient.testNotificationService();

        return ResponseEntity.ok(testResponse);
    }

}
