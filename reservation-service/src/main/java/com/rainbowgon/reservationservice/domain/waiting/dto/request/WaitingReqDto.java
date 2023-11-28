package com.rainbowgon.reservationservice.domain.waiting.dto.request;

import com.rainbowgon.reservationservice.domain.waiting.entity.Waiting;
import lombok.Getter;

@Getter
public class WaitingReqDto {

    private String themeId;
    private String targetDate;
    private String targetTime;

    public Waiting toEntity(String waitingId) {
        return Waiting.builder()
                .waitingId(waitingId)
                .themeId(themeId)
                .targetDate(targetDate)
                .targetTime(targetTime)
                .build();
    }
}
