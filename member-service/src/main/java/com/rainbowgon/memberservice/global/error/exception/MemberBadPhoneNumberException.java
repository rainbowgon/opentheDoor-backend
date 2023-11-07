package com.rainbowgon.memberservice.global.error.exception;

import com.rainbowgon.memberservice.global.error.errorCode.GlobalErrorCode;

public class MemberBadPhoneNumberException extends CustomException {

    public static final CustomException EXCEPTION = new MemberBadPhoneNumberException();

    public MemberBadPhoneNumberException() {
        super(GlobalErrorCode.MEMBER_BAD_PHONE_NUMBER);
    }
}
