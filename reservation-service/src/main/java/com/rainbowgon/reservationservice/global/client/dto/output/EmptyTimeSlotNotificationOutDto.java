package com.rainbowgon.reservationservice.global.client.dto.output;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rainbowgon.reservationservice.domain.waiting.entity.Waiting;
import com.rainbowgon.reservationservice.global.client.dto.input.FcmTokenInDto;
import com.rainbowgon.reservationservice.global.client.dto.input.ThemeBriefInfoInDto;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EmptyTimeSlotNotificationOutDto {

    private String memberId;
    private String fcmToken;
    private String themeId;
    private String title;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate targetDate;
    @JsonFormat(pattern = "hh:mm")
    private LocalTime targetTime;

    public static EmptyTimeSlotNotificationOutDto from(FcmTokenInDto fcmTokenDto,
                                                       ThemeBriefInfoInDto themeDto,
                                                       Waiting waiting) {
        return EmptyTimeSlotNotificationOutDto.builder()
                .memberId(fcmTokenDto.getMemberId())
                .fcmToken(fcmTokenDto.getFcmToken())
                .themeId(waiting.getThemeId())
                .title(themeDto.getTitle())
                .targetDate(waiting.getTargetDate())
                .targetTime(waiting.getTargetTime())
                .build();
    }

}
