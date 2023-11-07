package com.rainbowgon.notificationservice.global.error.exception;


import com.rainbowgon.notificationservice.global.error.errorCode.GlobalErrorCode;

public class JsonToStringException extends CustomException {

    public static final CustomException EXCEPTION = new JsonToStringException();

    public JsonToStringException() {
        super(GlobalErrorCode.JSON_TOSTRING_FAIL);
    }
}
