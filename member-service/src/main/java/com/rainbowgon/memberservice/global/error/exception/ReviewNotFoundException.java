package com.rainbowgon.memberservice.global.error.exception;

import com.rainbowgon.memberservice.global.error.errorCode.GlobalErrorCode;

public class ReviewNotFoundException extends CustomException {

    public static final CustomException EXCEPTION = new ReviewNotFoundException();

    public ReviewNotFoundException() {
        super(GlobalErrorCode.REVIEW_NOT_FOUND);
    }
}
