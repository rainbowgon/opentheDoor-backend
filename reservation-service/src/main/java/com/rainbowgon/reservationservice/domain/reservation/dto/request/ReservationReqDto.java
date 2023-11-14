package com.rainbowgon.reservationservice.domain.reservation.dto.request;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class ReservationReqDto {

    private LocalDate targetDate;
    private LocalTime targetTime;
    private Integer headcount;
    private Integer totalPrice;
    private String bookerName;
    private String bookerPhoneNumber;
    private String themeId;

}
