package com.rainbowgon.notification.domain.notification.controller;


import com.rainbowgon.notification.domain.notification.kafka.KafkaProducer;
import com.rainbowgon.notification.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final KafkaProducer kafkaProducer;
    @GetMapping
    public String v1() {
        kafkaProducer.sendMessage("this is a test message");

        return "test";
    }
}