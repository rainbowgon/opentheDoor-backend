package com.rainbowgon.notificationservice.domain.notification.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash("notification")
public class Notification {

    @Id
    private Long notificationId;
    @Indexed
    private Long profileId;
    private String themeId;
    private String title;
    private String body;
    private NotificationType notificationType;
    private ViewStatus viewStatus;

    @Builder
    public Notification(Long notificationId, Long profileId, String themeId, String title, String body,
                        NotificationType notificationType, ViewStatus viewStatus) {
        this.notificationId = notificationId;
        this.profileId = profileId;
        this.themeId = themeId;
        this.title = title;
        this.body = body;
        this.notificationType = notificationType;
        this.viewStatus = viewStatus;
    }
}
