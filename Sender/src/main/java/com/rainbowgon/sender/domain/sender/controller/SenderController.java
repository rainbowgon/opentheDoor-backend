package com.rainbowgon.sender.domain.sender.controller;

import com.rainbowgon.sender.domain.fcm.service.FCMInitializer;
import com.rainbowgon.sender.domain.fcm.service.FCMService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/senders")
@RequiredArgsConstructor
public class SenderController {

    private final FCMInitializer fcmInitializer;
    private final FCMService fcmService;

    @GetMapping
    public String v1() {
        fcmInitializer.initialize();
        fcmService.sendMessage("모바일 토큰");

        return "test";
    }
}
