package com.rainbowgon.reservationservice.domain.reservation.vo;

import com.rainbowgon.reservationservice.domain.reservation.entity.AvailableStatus;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class TimeVO {

    private LocalTime time;
    private AvailableStatus isAvailable;
}
