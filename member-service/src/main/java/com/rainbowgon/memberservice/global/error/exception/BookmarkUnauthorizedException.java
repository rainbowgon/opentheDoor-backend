package com.rainbowgon.memberservice.global.error.exception;

import com.rainbowgon.memberservice.global.error.errorCode.GlobalErrorCode;

public class BookmarkUnauthorizedException extends CustomException {

    public static final CustomException EXCEPTION = new BookmarkUnauthorizedException();

    public BookmarkUnauthorizedException() {
        super(GlobalErrorCode.BOOKMARK_UNAUTHORIZED);
    }
}
