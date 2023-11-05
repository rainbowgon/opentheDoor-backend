package com.rainbowgon.memberservice.domain.bookmark.client.dto.output;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class NotificationOutDto {

    private Long profileId;
    private String fcmToken;
    private String themeId;
    private String themeName;
    private String venueName;
    private LocalDate openDate;
    private LocalTime openTime;
}
