package com.rainbowgon.senderserver.domain.sender.entity;

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
    private Long memberId;
    private String themeId;
    private String title;
    private String body;
    private NotificationType notificationType;
    private ViewStatus viewStatus = ViewStatus.NOT_VIEWED;

    @Builder
    public Notification(Long notificationId, Long memberId, String themeId, String title, String body,
                        NotificationType notificationType) {
        this.notificationId = notificationId;
        this.memberId = memberId;
        this.themeId = themeId;
        this.title = title;
        this.body = body;
        this.notificationType = notificationType;
    }

    public static Notification from(NotificationLog notificationLog) {
        return Notification.builder()
                .notificationId(notificationLog.getId())
                .memberId(notificationLog.getMemberId())
                .themeId(notificationLog.getThemeId())
                .title(notificationLog.getTitle())
                .body(notificationLog.getBody())
                .notificationType(notificationLog.getNotificationType())
                .build();
    }
}