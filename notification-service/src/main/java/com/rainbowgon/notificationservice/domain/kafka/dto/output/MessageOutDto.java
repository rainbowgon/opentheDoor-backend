package com.rainbowgon.notificationservice.domain.kafka.dto.output;


import com.rainbowgon.notificationservice.domain.notification.entity.NotificationType;
import com.rainbowgon.notificationservice.global.client.dto.input.BookmarkInDto;
import com.rainbowgon.notificationservice.global.client.dto.input.ReservationInDto;
import com.rainbowgon.notificationservice.global.client.dto.input.WaitingInDto;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MessageOutDto {

    private String memberId;
    private String fcmToken;
    private String themeId;
    private String title;
    private String body;
    private NotificationType notificationType;


    public static MessageOutDto from(BookmarkInDto bookmarkInDto, String title, String body) {
        return MessageOutDto.builder()
                .memberId(bookmarkInDto.getMemberId())
                .fcmToken(bookmarkInDto.getFcmToken())
                .themeId(bookmarkInDto.getThemeId())
                .title(title)
                .body(body)
                .notificationType(NotificationType.BOOKMARK)
                .build();
    }

    public static MessageOutDto from(ReservationInDto reservationInDto, String title, String body) {
        return MessageOutDto.builder()
                .memberId(reservationInDto.getMemberId())
                .fcmToken(reservationInDto.getFcmToken())
                .themeId(reservationInDto.getThemeId())
                .title(title)
                .body(body)
                .notificationType(NotificationType.RESERVATION)
                .build();
    }

    public static MessageOutDto from(WaitingInDto waitingInDto, String title, String body) {
        return MessageOutDto.builder()
                .memberId(waitingInDto.getMemberId())
                .fcmToken(waitingInDto.getFcmToken())
                .themeId(waitingInDto.getThemeId())
                .title(title)
                .body(body)
                .notificationType(NotificationType.WAITING)
                .build();
    }
}
