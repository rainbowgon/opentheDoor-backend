package com.rainbowgon.searchservice.global.error.exception;

import com.rainbowgon.searchservice.global.error.errorCode.GlobalErrorCode;

public class PriceNotFoundException extends CustomException {

    public static final CustomException EXCEPTION = new PriceNotFoundException();

    public PriceNotFoundException() {
        super(GlobalErrorCode.PRICE_NOT_FOUND);
    }
}
