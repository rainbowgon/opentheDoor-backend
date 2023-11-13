package com.rainbowgon.notificationservice.global.error.exception;


import com.rainbowgon.notificationservice.global.error.errorCode.GlobalErrorCode;

public class RedisKeyNotFoundException extends CustomException {

    public static final CustomException EXCEPTION = new RedisKeyNotFoundException();

    public RedisKeyNotFoundException() {
        super(GlobalErrorCode.REDIS_KEY_NOT_FOUND);
    }
}
