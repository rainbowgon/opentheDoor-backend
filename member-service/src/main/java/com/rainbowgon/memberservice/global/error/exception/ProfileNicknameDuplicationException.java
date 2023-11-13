package com.rainbowgon.memberservice.global.error.exception;

import com.rainbowgon.memberservice.global.error.errorCode.GlobalErrorCode;

public class ProfileNicknameDuplicationException extends CustomException {

    public static final CustomException EXCEPTION = new ProfileNicknameDuplicationException();

    public ProfileNicknameDuplicationException() {
        super(GlobalErrorCode.PROFILE_NICKNAME_DUPLICATION);
    }
}
