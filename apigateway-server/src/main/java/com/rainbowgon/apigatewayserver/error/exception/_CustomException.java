package com.rainbowgon.apigatewayserver.error.exception;

import com.rainbowgon.apigatewayserver.error.dto.ErrorReason;
import com.rainbowgon.apigatewayserver.error.errorCode.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class _CustomException extends RuntimeException {

    private BaseErrorCode errorCode;

    public ErrorReason getErrorReason() {
        return this.errorCode.getErrorReason();
    }

}