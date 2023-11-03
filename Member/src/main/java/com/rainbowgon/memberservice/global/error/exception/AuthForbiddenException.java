package com.rainbowgon.member.global.error.exception;

import com.rainbowgon.member.global.error.errorCode.GlobalErrorCode;

public class AuthForbiddenException extends CustomException {

    public static final CustomException EXCEPTION = new AuthForbiddenException();

    public AuthForbiddenException() {
        super(GlobalErrorCode.AUTH_NO_AUTHORIZATION);
    }
}
