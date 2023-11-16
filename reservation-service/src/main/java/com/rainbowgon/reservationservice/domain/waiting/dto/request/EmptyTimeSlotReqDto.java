package com.rainbowgon.reservationservice.domain.waiting.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class EmptyTimeSlotReqDto {

    private String timeLineId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate targetDate;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime targetTime;

}
