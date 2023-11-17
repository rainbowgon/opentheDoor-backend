package com.rainbowgon.reservationservice.global.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rainbowgon.reservationservice.global.error.dto.ErrorReason;
import com.rainbowgon.reservationservice.global.error.dto.ErrorResponse;
import com.rainbowgon.reservationservice.global.error.errorCode.BaseErrorCode;
import com.rainbowgon.reservationservice.global.error.errorCode.GlobalErrorCode;
import com.rainbowgon.reservationservice.global.error.exception.CustomException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final ObjectMapper objectMapper;

    @ExceptionHandler(CustomException.class) // Business Exception
    public ResponseEntity<ErrorResponse> handleBusinessException(CustomException e,
                                                                 HttpServletRequest request) {
        BaseErrorCode code = e.getErrorCode();
        ErrorReason errorReason = code.getErrorReason();
        return ResponseEntity.status(errorReason.getStatus())
                .body(ErrorResponse.of(errorReason, request.getRequestURL().toString()));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Map> feignExceptionHandler(FeignException feignException) throws JsonProcessingException {

        String responseJson = feignException.contentUTF8();
        Map responseMap = objectMapper.readValue(responseJson, Map.class);

        return ResponseEntity
                .status(feignException.status())
                .body(responseMap);
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
