package com.rainbowgon.reservationservice.global.client.dto.output;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rainbowgon.reservationservice.domain.reservation.entity.Reservation;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class NotificationOutDto {

    private String memberId;
    private String fcmToken;
    private String themeId;
    private String title;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate targetDate;
    @JsonFormat(pattern = "hh:mm")
    private LocalTime targetTime;
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
