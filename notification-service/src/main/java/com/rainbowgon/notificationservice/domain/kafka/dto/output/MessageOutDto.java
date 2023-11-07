package com.rainbowgon.notificationservice.domain.kafka.dto.output;


import com.rainbowgon.notificationservice.domain.notification.client.dto.input.BookmarkInDto;
import com.rainbowgon.notificationservice.domain.notification.client.dto.input.ReservationInDto;
import com.rainbowgon.notificationservice.domain.notification.client.dto.input.WaitingInDto;
import com.rainbowgon.notificationservice.domain.notification.entity.NotificationType;
import com.rainbowgon.notificationservice.domain.notification.entity.ViewStatus;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MessageOutDto {

    private Long profileId;
    private String fcmToken;
    private Long themeId;
    private String title;
    private String body;
    private NotificationType notificationType;
    private ViewStatus viewStatus;


    public static MessageOutDto from(BookmarkInDto bookmarkInDto, String title, String body) {
        return MessageOutDto.builder()
                .profileId(bookmarkInDto.getProfileId())
                .fcmToken(bookmarkInDto.getFcmToken())
                .themeId(bookmarkInDto.getThemeId())
                .title(title)
                .body(body)
                .notificationType(NotificationType.BOOKMARK)
                .viewStatus(ViewStatus.NOT_VIEWED)
                .build();
    }

    public static MessageOutDto from(ReservationInDto reservationInDto, String title, String body) {
        return MessageOutDto.builder()
                .profileId(reservationInDto.getProfileId())
                .fcmToken(reservationInDto.getFcmToken())
                .themeId(reservationInDto.getThemeId())
                .title(title)
                .body(body)
                .notificationType(NotificationType.RESERVATION)
                .viewStatus(ViewStatus.NOT_VIEWED)
                .build();
    }

    public static MessageOutDto from(WaitingInDto waitingInDto, String title, String body) {
        return MessageOutDto.builder()
                .profileId(waitingInDto.getProfileId())
                .fcmToken(waitingInDto.getFcmToken())
                .themeId(waitingInDto.getThemeId())
                .title(title)
                .body(body)
                .notificationType(NotificationType.WAITING)
                .viewStatus(ViewStatus.NOT_VIEWED)
                .build();
    }
}
