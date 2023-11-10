package com.rainbowgon.apigatewayserver.error.exception;

import com.rainbowgon.apigatewayserver.error.errorCode.CustomErrorCode;

public class InvalidTokenException extends _CustomException {

    public static final _CustomException EXCEPTION = new InvalidTokenException();

    public InvalidTokenException() {
        super(CustomErrorCode.INVALID_TOKEN);
    }
}
