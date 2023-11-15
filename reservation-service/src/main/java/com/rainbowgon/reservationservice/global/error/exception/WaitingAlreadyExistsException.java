package com.rainbowgon.reservationservice.global.error.exception;

import com.rainbowgon.reservationservice.global.error.errorCode.GlobalErrorCode;

public class WaitingAlreadyExistsException extends CustomException {

    public static final CustomException EXCEPTION = new WaitingAlreadyExistsException();

    public WaitingAlreadyExistsException() {
        super(GlobalErrorCode.WAITING_ALREADY_EXISTS);
    }
}