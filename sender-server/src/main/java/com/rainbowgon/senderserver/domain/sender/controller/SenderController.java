package com.rainbowgon.senderserver.domain.sender.controller;

import com.rainbowgon.senderserver.domain.fcm.service.FCMService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/senders")
@RequiredArgsConstructor
public class SenderController {

    private final FCMService fcmService;

    @GetMapping("/test")
    public String test() {
        fcmService.sendMessage("cydOvdiQSn6nGbC9KdMvuW:APA91bFw_dqqHDQ3S8kCdZUejr-Eq3jH9NyE2BFndxBnDirE2D" +
                                       "-QwXPtgikS_BooMMTzQ7gKAYndET42_E6YMtk9YWmALu11URgIAwA9Z1oENnXqjWpFvguEAR0d6L1TowRHkYYSdQaP", "스프링에서 간 알림", "스프링에서 간 알림");
        System.out.println("테스트 성공");

        return "작업 서버 FCM 테스트 성공";
    }
}
