package com.rainbowgon.reservationservice.global.client.dto.output;

import com.rainbowgon.reservationservice.domain.reservation.entity.Reservation;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class NotificationOutDto {

    private String memberId;
    private String fcmToken;
    private String themeId;
    private String title;
    private String targetDate;
    private String targetTime;
    private NotificationType reservationType;

    public static NotificationOutDto success(String memberId, String fcmToken, String title,
                                             Reservation reservation) {
        return NotificationOutDto.builder()
                .memberId(memberId)
                .fcmToken(fcmToken)
                .themeId(reservation.getThemeId())
                .title(title)
                .targetDate(reservation.getTargetDate())
                .targetTime(reservation.getTargetTime())
                .reservationType(NotificationType.RESERVATION)
                .build();
    }

}
