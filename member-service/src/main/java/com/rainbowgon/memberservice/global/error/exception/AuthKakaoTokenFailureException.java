package com.rainbowgon.memberservice.global.error.exception;

import com.rainbowgon.memberservice.global.error.errorCode.GlobalErrorCode;

public class AuthKakaoTokenFailureException extends CustomException {

    public static final CustomException EXCEPTION = new AuthKakaoTokenFailureException();

    public AuthKakaoTokenFailureException() {
        super(GlobalErrorCode.AUTH_KAKAO_TOKEN_FAILURE);
    }
}
