package com.rainbowgon.sender.domain.controller;

import com.rainbowgon.sender.domain.service.FCMInitializer;
import com.rainbowgon.sender.domain.service.FCMService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sender")
@RequiredArgsConstructor
public class SenderController {

    private final FCMInitializer fcmInitializer;
    private final FCMService fcmService;

    @GetMapping
    public String v1() {
        fcmInitializer.initialize();
        fcmService.sendMessage("고세훈 S10토큰");

        return "test";
    }
}
