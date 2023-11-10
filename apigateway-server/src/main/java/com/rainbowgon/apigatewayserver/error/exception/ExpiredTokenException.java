package com.rainbowgon.apigatewayserver.error.exception;

import com.rainbowgon.apigatewayserver.error.errorCode.CustomErrorCode;

public class ExpiredTokenException extends _CustomException {

    public static final _CustomException EXCEPTION = new ExpiredTokenException();

    public ExpiredTokenException() {
        super(CustomErrorCode.TOKEN_EXPIRED);
    }
}
