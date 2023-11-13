package com.rainbowgon.memberservice.global.error.exception;

import com.rainbowgon.memberservice.global.error.errorCode.GlobalErrorCode;

public class ProfileUnauthorizedException extends CustomException {

    public static final CustomException EXCEPTION = new ProfileUnauthorizedException();

    public ProfileUnauthorizedException() {
        super(GlobalErrorCode.PROFILE_UNAUTHORIZED);
    }
}
