package com.rainbowgon.senderserver.global.error.exception;

import com.rainbowgon.senderserver.global.error.errorCode.GlobalErrorCode;

public class FCMFileNotFoundException extends CustomException {

    public static final CustomException EXCEPTION = new FCMFileNotFoundException();

    public FCMFileNotFoundException() {
        super(GlobalErrorCode.FCM_FILE_NOT_FOUND);
    }
}
