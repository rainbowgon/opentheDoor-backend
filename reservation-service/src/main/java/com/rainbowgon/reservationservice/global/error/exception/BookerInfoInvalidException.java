package com.rainbowgon.reservationservice.global.error.exception;

import com.rainbowgon.reservationservice.global.error.errorCode.GlobalErrorCode;


public class BookerInfoInvalidException extends CustomException {

    public static final CustomException EXCEPTION = new BookerInfoInvalidException();

    public BookerInfoInvalidException() {
        super(GlobalErrorCode.BOOKER_INFO_INVALID);
    }
}