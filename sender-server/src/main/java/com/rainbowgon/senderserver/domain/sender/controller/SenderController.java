package com.rainbowgon.senderserver.domain.sender.controller;

import com.rainbowgon.senderserver.domain.fcm.service.FCMService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/senders")
@RequiredArgsConstructor
public class SenderController {

    private final FCMService fcmService;

    @GetMapping("/test/{token}")
    public String test(@PathVariable(name = "token") String token) {

        fcmService.sendMessage(token, "스프링에서 간 알림", "스프링에서 간 알림");
        System.out.println("테스트 성공");

        return "작업 서버 FCM 테스트 성공";
    }

}
