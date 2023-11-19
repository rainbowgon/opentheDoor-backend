package com.rainbowgon.reservationservice.domain.waiting.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rainbowgon.reservationservice.domain.waiting.entity.Waiting;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class WaitingReqDto {

    private String themeId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate targetDate;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime targetTime;

    public Waiting toEntity(String waitingId) {
        return Waiting.builder()
                .waitingId(waitingId)
                .themeId(themeId)
                .targetDate(targetDate)
                .targetTime(targetTime)
                .build();
    }
}
