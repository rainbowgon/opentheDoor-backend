package com.rainbowgon.notificationservice.domain.notification.client.dto.in;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkInDto {

    private Long profileId;
    private String fcmToken;
    private Long themeId;
    private String themeName;
    private String venueName;
    private LocalTime openTime;

}
