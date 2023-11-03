package com.rainbowgon.sender.global.error.exception;

import com.rainbowgon.sender.global.error.errorCode.BaseErrorCode;
import com.rainbowgon.sender.global.error.errorCode.GlobalErrorCode;

public class FCMInitializerFailException extends CustomException {

    public static final CustomException EXCEPTION = new FCMInitializerFailException();

    public FCMInitializerFailException() {
        super(GlobalErrorCode.FCM_INITIALIZER_FAIL);
    }
}
