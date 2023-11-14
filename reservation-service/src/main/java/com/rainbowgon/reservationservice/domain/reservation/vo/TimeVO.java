package com.rainbowgon.reservationservice.domain.reservation.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rainbowgon.reservationservice.domain.reservation.entity.AvailableStatus;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class TimeVO {

    @JsonFormat(pattern = "hh:mm")
    private LocalTime time;
    private AvailableStatus isAvailable;
}
