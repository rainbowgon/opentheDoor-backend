package com.rainbowgon.reservationservice.domain.reservation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class WaitingReqDto {

    private String themeId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate targetDate;
    @JsonFormat(pattern = "hh:mm")
    private LocalTime targetTime;

}
