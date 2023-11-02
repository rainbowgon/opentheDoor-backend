package com.rainbowgon.member.global.error.exception;

import com.rainbowgon.member.global.error.errorCode.GlobalErrorCode;

public class BookmarkNotFoundException extends CustomException {

    public static final CustomException EXCEPTION = new BookmarkNotFoundException();

    public BookmarkNotFoundException() {
        super(GlobalErrorCode.BOOKMARK_NOT_FOUND);
    }
}
