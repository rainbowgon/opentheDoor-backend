package com.rainbowgon.notificationservice.domain.notification.client.dto.in;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationInDto {

    private Long profileId;
    private String FCMToken;
    private Long themeId;
    private String themeName;
    private LocalDate reservationDate;
    private LocalTime reservationTime;

}
