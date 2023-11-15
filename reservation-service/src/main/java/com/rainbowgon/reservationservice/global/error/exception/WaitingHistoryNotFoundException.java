package com.rainbowgon.reservationservice.global.error.exception;

import com.rainbowgon.reservationservice.global.error.errorCode.GlobalErrorCode;

public class WaitingHistoryNotFoundException extends CustomException {

    public static final CustomException EXCEPTION = new WaitingHistoryNotFoundException();

    public WaitingHistoryNotFoundException() {
        super(GlobalErrorCode.WAITING_HISTORY_NOT_FOUND);
    }
}