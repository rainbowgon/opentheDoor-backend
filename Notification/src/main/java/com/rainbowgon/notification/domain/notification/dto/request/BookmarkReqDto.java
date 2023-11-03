package com.rainbowgon.notification.domain.notification.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkReqDto {

    private Long profileId;
    private Long themeId;
    private String themeName;
    private String venueName;
    private LocalTime openTime;

}
