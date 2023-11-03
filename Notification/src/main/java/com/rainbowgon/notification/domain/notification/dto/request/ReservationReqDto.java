package com.rainbowgon.notification.domain.notification.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationReqDto {

    private Long profileId;
    private Long themeId;
    private String themeName;
    private LocalDate reservationDate;
    private LocalTime reservationTime;

}
