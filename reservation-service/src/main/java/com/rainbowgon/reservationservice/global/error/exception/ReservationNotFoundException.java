package com.rainbowgon.reservationservice.global.error.exception;

import com.rainbowgon.reservationservice.global.error.errorCode.GlobalErrorCode;


public class ReservationNotFoundException extends CustomException {

    public static final CustomException EXCEPTION = new ReservationNotFoundException();

    public ReservationNotFoundException() {
        super(GlobalErrorCode.RESERVATION_NOT_FOUND);
    }
}