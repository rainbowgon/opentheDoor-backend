package com.rainbowgon.memberservice.domain.client;

import com.rainbowgon.memberservice.domain.client.dto.output.NotificationOutDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "notification-service")
public interface NotificationServiceClient {

    @PostMapping("/clients/notifications/member")
    void sendBookmarkNotification(@RequestBody List<NotificationOutDto> notificationOutDtoList);

    @GetMapping("/clients/notifications/test")
    String testNotificationService();

}
