package com.rainbowgon.memberservice.global.client.dto.output;

import lombok.*;

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
    private String venue;
    private LocalTime openTime;
}
