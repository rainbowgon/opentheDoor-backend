package com.rainbowgon.memberservice.global.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rainbowgon.memberservice.global.error.dto.ErrorReason;
import com.rainbowgon.memberservice.global.error.dto.ErrorResponse;
import com.rainbowgon.memberservice.global.error.errorCode.BaseErrorCode;
import com.rainbowgon.memberservice.global.error.errorCode.GlobalErrorCode;
import com.rainbowgon.memberservice.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

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

    private void setErrorResponse(CustomException customException, String requestUrl, HttpServletResponse response) {

        BaseErrorCode code = customException.getErrorCode();
        ErrorReason errorReason = code.getErrorReason();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        response.setStatus(errorReason.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        ErrorResponse errorResponse = ErrorResponse.of(errorReason, requestUrl);

        try {
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}