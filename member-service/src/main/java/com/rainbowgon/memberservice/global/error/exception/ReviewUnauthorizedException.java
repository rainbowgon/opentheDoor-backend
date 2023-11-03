package com.rainbowgon.memberservice.global.error.exception;

import com.rainbowgon.memberservice.global.error.errorCode.GlobalErrorCode;

public class ReviewUnauthorizedException extends CustomException {

    public static final CustomException EXCEPTION = new ReviewUnauthorizedException();

    public ReviewUnauthorizedException() {
        super(GlobalErrorCode.REVIEW_UNAUTHORIZED);
    }
}
