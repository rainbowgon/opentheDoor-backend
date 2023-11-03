package com.rainbowgon.memberservice.global.error.exception;

import com.rainbowgon.memberservice.global.error.errorCode.GlobalErrorCode;

public class ProfileNotFoundException extends CustomException {

    public static final CustomException EXCEPTION = new ProfileNotFoundException();

    public ProfileNotFoundException() {
        super(GlobalErrorCode.PROFILE_NOT_FOUND);
    }
}
