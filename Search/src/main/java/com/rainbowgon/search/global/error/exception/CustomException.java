package com.rainbowgon.search.global.error.exception;

import com.rainbowgon.search.global.error.dto.ErrorReason;
import com.rainbowgon.search.global.error.errorCode.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {

    private BaseErrorCode errorCode;

    public ErrorReason getErrorReason() {
        return this.errorCode.getErrorReason();
    }
}
