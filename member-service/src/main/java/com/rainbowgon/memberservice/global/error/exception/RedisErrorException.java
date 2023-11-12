package com.rainbowgon.memberservice.global.error.exception;

import com.rainbowgon.memberservice.global.error.errorCode.GlobalErrorCode;

public class RedisErrorException extends CustomException {

    public static final CustomException EXCEPTION = new RedisErrorException();

    public RedisErrorException() {
        super(GlobalErrorCode.REDIS_SERVER_ERROR);
    }
}
