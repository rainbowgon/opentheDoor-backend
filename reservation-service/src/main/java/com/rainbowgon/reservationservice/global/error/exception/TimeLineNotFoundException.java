package com.rainbowgon.reservationservice.global.error.exception;

import com.rainbowgon.reservationservice.global.error.errorCode.GlobalErrorCode;


public class TimeLineNotFoundException extends CustomException {

    public static final CustomException EXCEPTION = new TimeLineNotFoundException();

    public TimeLineNotFoundException() {
        super(GlobalErrorCode.TIMELINE_NOT_FOUND);
    }
}