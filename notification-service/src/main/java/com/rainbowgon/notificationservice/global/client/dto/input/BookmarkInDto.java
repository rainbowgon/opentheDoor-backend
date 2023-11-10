package com.rainbowgon.notificationservice.global.client.dto.input;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkInDto {

    private Long profileId;
    private String fcmToken;
    private String themeId;
    private String themeName;
    private String venueName;
    private LocalTime openTime;

}
