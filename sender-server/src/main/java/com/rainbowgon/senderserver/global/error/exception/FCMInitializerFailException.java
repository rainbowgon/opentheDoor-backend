package com.rainbowgon.senderserver.global.error.exception;

import com.rainbowgon.senderserver.global.error.errorCode.GlobalErrorCode;

public class FCMInitializerFailException extends CustomException {

    public static final CustomException EXCEPTION = new FCMInitializerFailException();

    public FCMInitializerFailException() {
        super(GlobalErrorCode.FCM_INITIALIZER_FAIL);
    }
}
