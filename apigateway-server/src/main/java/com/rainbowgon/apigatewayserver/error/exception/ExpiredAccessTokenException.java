package com.rainbowgon.apigatewayserver.error.exception;

import com.rainbowgon.apigatewayserver.error.errorCode.CustomErrorCode;

public class ExpiredAccessTokenException extends _CustomException {

    public static final _CustomException EXCEPTION = new ExpiredAccessTokenException();

    public ExpiredAccessTokenException() {
        super(CustomErrorCode.TOKEN_EXPIRED);
    }
}
