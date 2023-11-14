package com.rainbowgon.memberservice.global.client;

import com.rainbowgon.memberservice.global.client.dto.output.NotificationOutDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "notification-service")
public interface NotificationServiceClient {

    @PostMapping("/clients/notifications/bookmark")
    void sendBookmarkNotification(@RequestBody List<NotificationOutDto> notificationOutDtoList);

    @GetMapping("/clients/notifications/test")
    String testNotificationService();

}
