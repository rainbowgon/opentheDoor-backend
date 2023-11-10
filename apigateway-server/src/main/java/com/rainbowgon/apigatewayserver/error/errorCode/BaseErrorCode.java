package com.rainbowgon.apigatewayserver.error.errorCode;

import com.rainbowgon.apigatewayserver.error.dto.ErrorReason;

public interface BaseErrorCode {

    ErrorReason getErrorReason();

}