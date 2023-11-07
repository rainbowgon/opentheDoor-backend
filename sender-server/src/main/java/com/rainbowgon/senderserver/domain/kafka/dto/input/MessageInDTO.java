package com.rainbowgon.senderserver.domain.kafka.dto.in;


import com.rainbowgon.senderserver.domain.sender.entity.NotificationType;
import com.rainbowgon.senderserver.domain.sender.entity.ViewStatus;
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
    private ViewStatus viewStatus;

}
