package com.rainbowgon.notificationservice.global.client.dto.input;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WaitingInDto {

    private Long memberId;
    private String fcmToken;
    private String themeId;
    private String title;
    private LocalDate targetDate;
    private LocalTime targetTime;

}
