package com.rainbowgon.apigatewayserver.error.errorCode;

import com.rainbowgon.apigatewayserver.error.dto.ErrorReason;
import com.rainbowgon.apigatewayserver.error.dto.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
@AllArgsConstructor
public enum CustomErrorCode implements BaseErrorCode {

    NO_AUTHORIZATION(FORBIDDEN, "AUTH-403-1", "Request Header에 Authorization이 존재하지 않습니다."),
    TOKEN_EXPIRED(UNAUTHORIZED, "AUTH-401-1", "만료된 토큰입니다."),
    ;


    private HttpStatus status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.builder()
                .reason(reason)
                .code(code)
                .status(status)
                .build();
    }
}
