package com.rainbowgon.memberservice.domain.bookmark.client.dto.output;

import lombok.*;

import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class NotificationOutDto {

    private String profileId;
    private String fcmToken;
    private String themeId;
    private String themeName;
    private String venueName;
    private LocalTime openTime;
}