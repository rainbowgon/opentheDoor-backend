package com.rainbowgon.senderserver.domain.kafka.dto.input;


import com.rainbowgon.senderserver.domain.sender.entity.NotificationLog;
import com.rainbowgon.senderserver.domain.sender.entity.NotificationType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageInDTO {

    private Long profileId;
    private String fcmToken;
    private Long themeId;
    private String title;
    private String body;
    private NotificationType notificationType;

    public NotificationLog toEntity() {
        return NotificationLog.builder()
                .profileId(profileId)
                .themeId(themeId)
                .title(title)
                .body(body)
                .notificationType(notificationType)
                .build();
    }
}