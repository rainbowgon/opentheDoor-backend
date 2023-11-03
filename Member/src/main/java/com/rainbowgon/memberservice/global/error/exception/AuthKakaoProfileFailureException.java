package com.rainbowgon.member.global.error.exception;

import com.rainbowgon.member.global.error.errorCode.GlobalErrorCode;

public class AuthKakaoProfileFailureException extends CustomException {

    public static final CustomException EXCEPTION = new AuthKakaoProfileFailureException();

    public AuthKakaoProfileFailureException() {
        super(GlobalErrorCode.AUTH_KAKAO_PROFILE_FAILURE);
    }
}
