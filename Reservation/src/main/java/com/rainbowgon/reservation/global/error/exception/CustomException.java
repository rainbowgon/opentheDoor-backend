package com.rainbowgon.reservation.global.error.exception;

import com.rainbowgon.reservation.global.error.dto.ErrorReason;
import com.rainbowgon.reservation.global.error.errorCode.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {

    private BaseErrorCode errorCode;

    public ErrorReason getErrorReason() {
        return this.errorCode.getErrorReason();
    }
}
