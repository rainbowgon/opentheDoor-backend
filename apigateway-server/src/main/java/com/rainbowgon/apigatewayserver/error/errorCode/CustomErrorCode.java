package com.rainbowgon.apigatewayserver.error.errorCode;

import com.rainbowgon.apigatewayserver.error.dto.ErrorReason;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
@AllArgsConstructor
public enum CustomErrorCode implements BaseErrorCode {

    NO_AUTHORIZATION(FORBIDDEN, "AUTH-403-1", "Request Header에 Authorization이 존재하지 않습니다."),
    TOKEN_EXPIRED(UNAUTHORIZED, "AUTH-401-1", "토큰이 만료되었습니다."),
    INVALID_TOKEN(UNAUTHORIZED, "AUTH-401-2", "토큰이 유효하지 않습니다."),
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
