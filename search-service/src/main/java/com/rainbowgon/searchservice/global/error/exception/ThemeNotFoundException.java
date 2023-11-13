package com.rainbowgon.searchservice.global.error.exception;

import com.rainbowgon.searchservice.global.error.errorCode.GlobalErrorCode;

public class ThemeNotFoundException extends CustomException {

    public static final CustomException EXCEPTION = new ThemeNotFoundException();

    public ThemeNotFoundException() {
        super(GlobalErrorCode.THEME_NOT_FOUND);
    }
}
