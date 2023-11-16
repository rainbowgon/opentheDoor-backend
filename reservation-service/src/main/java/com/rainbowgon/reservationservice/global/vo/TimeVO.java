package com.rainbowgon.reservationservice.global.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class TimeVO {

    @JsonFormat(pattern = "hh:mm")
    private LocalTime time;
    private AvailableStatus isAvailable;
}
