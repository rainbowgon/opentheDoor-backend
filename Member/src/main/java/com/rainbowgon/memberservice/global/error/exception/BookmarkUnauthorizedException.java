package com.rainbowgon.member.global.error.exception;

import com.rainbowgon.member.global.error.errorCode.GlobalErrorCode;

public class BookmarkUnauthorizedException extends CustomException {

    public static final CustomException EXCEPTION = new BookmarkUnauthorizedException();

    public BookmarkUnauthorizedException() {
        super(GlobalErrorCode.BOOKMARK_UNAUTHORIZED);
    }
}
