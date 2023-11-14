package com.rainbowgon.reservationservice.domain.reservation.dto.request;

import lombok.Getter;

@Getter
public class UnauthReservationDetailReqDto {

    private String bookerName;
    private String bookerPhoneNumber;
    private Long reservationNumber;

}
