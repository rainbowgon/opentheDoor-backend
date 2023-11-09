package com.rainbowgon.senderserver.domain.sender.controller;

import com.rainbowgon.senderserver.domain.fcm.service.FCMInitializer;
import com.rainbowgon.senderserver.domain.fcm.service.FCMService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/senders")
@RequiredArgsConstructor
public class SenderController {

    // 컨트롤러 삭제 예정
    private final FCMInitializer fcmInitializer;
    private final FCMService fcmService;

    @GetMapping
    public String test() {
        fcmInitializer.initialize();
        fcmService.sendMessage("모바일 토큰", "스프링에서 간 알림", "스프링에서 간 알림");

        return "test";
    }
}
