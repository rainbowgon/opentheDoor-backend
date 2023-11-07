package com.rainbowgon.senderserver.domain.sender.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash("notification")
public class Notification {

    @Id
    private Long id;
    private Long profileId;
    private Long themeId;
    private String title;
    private String body;
    private NotificationType notificationType;
    private ViewStatus viewStatus = ViewStatus.NOT_VIEWED;

    @Builder
    public Notification(Long id, Long profileId, Long themeId, String title, String body,
                        NotificationType notificationType, ViewStatus viewStatus) {
        this.id = id;
        this.profileId = profileId;
        this.themeId = themeId;
        this.title = title;
        this.body = body;
        this.notificationType = notificationType;
    }
}