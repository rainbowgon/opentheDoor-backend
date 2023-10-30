package com.rainbowgon.member.global.error.exception;

import com.rainbowgon.member.global.error.errorCode.GlobalErrorCode;

public class AuthKakaoTokenFailureException extends CustomException {

    public static final CustomException EXCEPTION = new AuthKakaoTokenFailureException();

    public AuthKakaoTokenFailureException() {
        super(GlobalErrorCode.AUTH_KAKAO_TOKEN_FAILURE);
    }
}
