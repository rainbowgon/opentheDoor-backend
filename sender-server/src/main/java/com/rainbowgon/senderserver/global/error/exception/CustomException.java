package com.rainbowgon.senderserver.global.error.exception;

import com.rainbowgon.senderserver.global.error.dto.ErrorReason;
import com.rainbowgon.senderserver.global.error.errorCode.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {

    private BaseErrorCode errorCode;

    public ErrorReason getErrorReason() {
        return errorCode.getErrorReason();
    }
}
