package com.rainbowgon.member.global.error.exception;

import com.rainbowgon.member.global.error.errorCode.GlobalErrorCode;

public class ProfileUnauthorizedException extends CustomException {

    public static final CustomException EXCEPTION = new ProfileUnauthorizedException();

    public ProfileUnauthorizedException() {
        super(GlobalErrorCode.PROFILE_UNAUTHORIZED);
    }
}
