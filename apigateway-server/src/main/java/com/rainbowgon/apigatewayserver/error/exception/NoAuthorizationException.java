package com.rainbowgon.apigatewayserver.error.exception;

import com.rainbowgon.apigatewayserver.error.errorCode.CustomErrorCode;

public class NoAuthorizationException extends _CustomException {

    public static final _CustomException EXCEPTION = new NoAuthorizationException();

    public NoAuthorizationException() {
        super(CustomErrorCode.NO_AUTHORIZATION);
    }
}
