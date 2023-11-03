package com.rainbowgon.memberservice.global.error.exception;

import com.rainbowgon.memberservice.global.error.errorCode.GlobalErrorCode;

public class AuthTokenExpiredException extends CustomException {

    public static final CustomException EXCEPTION = new AuthTokenExpiredException();

    public AuthTokenExpiredException() {
        super(GlobalErrorCode.AUTH_TOKEN_EXPIRED);
    }
}
