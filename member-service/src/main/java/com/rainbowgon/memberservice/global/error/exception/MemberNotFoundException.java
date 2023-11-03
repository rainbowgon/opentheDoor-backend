package com.rainbowgon.memberservice.global.error.exception;

import com.rainbowgon.memberservice.global.error.errorCode.GlobalErrorCode;

public class MemberNotFoundException extends CustomException {

    public static final CustomException EXCEPTION = new MemberNotFoundException();

    public MemberNotFoundException() {
        super(GlobalErrorCode.MEMBER_NOT_FOUND);
    }
}
