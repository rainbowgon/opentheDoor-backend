package com.rainbowgon.senderserver.domain.kafka.dto.input;


import com.rainbowgon.senderserver.domain.sender.entity.NotificationLog;
import com.rainbowgon.senderserver.domain.sender.entity.NotificationType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageInDto {

    private String memberId;
    private String fcmToken;
    private String themeId;
    private String title;
    private String body;
    private NotificationType notificationType;

    public NotificationLog toEntity() {
        return NotificationLog.builder()
                .memberId(memberId)
                .themeId(themeId)
                .title(title)
                .body(body)
                .notificationType(notificationType)
                .build();
    }
}