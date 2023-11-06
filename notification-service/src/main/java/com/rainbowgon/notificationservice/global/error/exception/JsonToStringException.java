package com.rainbowgon.notificationservice.global.error.exception;


import com.rainbowgon.notificationservice.global.error.errorCode.GlobalErrorCode;

public class JsonProcessingException extends CustomException {

    public static final CustomException EXCEPTION = new JsonProcessingException();

    public JsonProcessingException() {
        super(GlobalErrorCode.JSON_TOSTRING_FAIL);
    }
}
