package com.rainbowgon.memberservice.domain.bookmark.client.dto;

import com.rainbowgon.memberservice.domain.bookmark.client.dto.output.NotificationOutDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "notification-service")
public interface NotificationServiceClient {

    @PostMapping("/notifications/member")
    void sendBookmarkNotification(@RequestBody List<NotificationOutDto> notificationOutDtoList);
}
