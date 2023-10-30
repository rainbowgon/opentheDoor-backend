package com.rainbowgon.member.global.error.exception;

import com.rainbowgon.member.global.error.errorCode.GlobalErrorCode;

public class ProfileNicknameDuplicationException extends CustomException {

    public static final CustomException EXCEPTION = new ProfileNicknameDuplicationException();

    public ProfileNicknameDuplicationException() {
        super(GlobalErrorCode.PROFILE_NICKNAME_DUPLICATION);
    }
}
