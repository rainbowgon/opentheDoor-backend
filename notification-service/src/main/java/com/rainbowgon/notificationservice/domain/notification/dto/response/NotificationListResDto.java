package com.rainbowgon.notificationservice.domain.notification.dto.response;

import com.rainbowgon.notificationservice.domain.notification.entity.Notification;
import com.rainbowgon.notificationservice.domain.notification.entity.NotificationType;
import com.rainbowgon.notificationservice.domain.notification.entity.ViewStatus;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class NotificationListResDto {

    private Long notificationId;
    private Long themeId;
    private String title;
    private String body;
    private NotificationType notificationType;
    private ViewStatus viewStatus;

    public static NotificationListResDto from(Notification notification) {
        return NotificationListResDto.builder()
                .notificationId(notification.getNotificationId())
                .themeId(notification.getThemeId())
                .title(notification.getTitle())
                .body(notification.getBody())
                .notificationType(notification.getNotificationType())
                .viewStatus(notification.getViewStatus())
                .build();
    }


}
