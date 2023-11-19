package com.rainbowgon.memberservice.global.error.exception;

import com.rainbowgon.memberservice.global.error.errorCode.GlobalErrorCode;

public class AwsS3ErrorException extends CustomException {

    public static final CustomException EXCEPTION = new AwsS3ErrorException();

    public AwsS3ErrorException() {
        super(GlobalErrorCode.AWS_S3_SERVER_ERROR);
    }
}
