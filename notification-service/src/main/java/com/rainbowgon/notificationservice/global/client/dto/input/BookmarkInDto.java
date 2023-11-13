package com.rainbowgon.notificationservice.global.client.dto.input;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkInDto {

    private Long memberId;
    private String fcmToken;
    private String themeId;
    private String title;
    private String venue;
    private LocalTime openTime;

}
