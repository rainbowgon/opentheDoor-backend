package com.rainbowgon.member.global.error;

import com.rainbowgon.member.global.error.dto.ErrorReason;
import com.rainbowgon.member.global.error.dto.ErrorResponse;
import com.rainbowgon.member.global.error.errorCode.BaseErrorCode;
import com.rainbowgon.member.global.error.errorCode.GlobalErrorCode;
import com.rainbowgon.member.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class) // Business Exception
    public ResponseEntity<ErrorResponse> handleBusinessException(CustomException e, HttpServletRequest request) {
        BaseErrorCode code = e.getErrorCode();
        ErrorReason errorReason = code.getErrorReason();
        return ResponseEntity.status(errorReason.getStatus())
                .body(ErrorResponse.of(errorReason, request.getRequestURL().toString()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request) {
        e.printStackTrace();
        GlobalErrorCode errorCode = GlobalErrorCode.CUSTOM_INTERNAL_SERVER_ERROR;
        ErrorReason errorReason = errorCode.getErrorReason();
        return ResponseEntity.status(errorCode.getStatus())
                .body(ErrorResponse.of(errorReason, request.getRequestURL().toString()));
    }

}