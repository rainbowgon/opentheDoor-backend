package com.rainbowgon.sender.domain.controller;

import com.rainbowgon.sender.domain.fcm.FCMInitializer;
import com.rainbowgon.sender.domain.fcm.FCMService;
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
        fcmService.sendMessage("eUx1lfVlRD-sbq0gsH7VVI:APA91bGTPBMRli6tFf5AherbKLqj7G4TMqia6OsXUy-Al9S48haWnIbDnvSCzAE7K9ZjG0xg2tW0mZvnRHoDNl4Lklg5LJ0R9FXoGrDkHA-Qw6z28JqxM2KGDm_OaPGZ-d6cKiCQkvH3");

        return "test";
    }
}
