package com.rainbowgon.memberservice.domain.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "notification-service")
public interface NotificationServiceClient {

    @GetMapping("/clients/notifications/test")
    String testNotificationService();
}
