package com.rainbowgon.reservationservice.global.error.exception;

import com.rainbowgon.reservationservice.global.error.errorCode.GlobalErrorCode;


public class DatetimeInvalidException extends CustomException {

    public static final CustomException EXCEPTION = new DatetimeInvalidException();

    public DatetimeInvalidException() {
        super(GlobalErrorCode.DATETIME_INVALID);
    }
}