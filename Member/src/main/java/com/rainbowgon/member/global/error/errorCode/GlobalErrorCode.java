package com.rainbowgon.member.global.error.errorCode;

import com.rainbowgon.member.global.error.dto.ErrorReason;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode implements BaseErrorCode {

    /**
     * 예시
     */
    EXAMPLE_NOT_FOUND(NOT_FOUND, "EXAMPLE-404-1", "예시를 찾을 수 없는 오류입니다."),

    /* Member */
    MEMBER_NOT_FOUND(NOT_FOUND, "MEMBER-404-1", "해당 회원을 찾을 수 없습니다."),
    MEMBER_PHONE_NUMBER_DUPLICATION(CONFLICT, "MEMBER-409-1", "이미 존재하는 회원(전화번호)입니다."),

    /* Auth */
    AUTH_NO_AUTHORIZATION(FORBIDDEN, "AUTH-403-1", "Request header에 Authorization이 존재하지 않습니다."),
    AUTH_TOKEN_EXPIRED(UNAUTHORIZED, "AUTH-401-1", "만료된 토큰입니다."),

    CUSTOM_INTERNAL_SERVER_ERROR(INTERNAL_SERVER_ERROR, "GLOBAL-500-1", "서버 오류. 관리자에게 문의 부탁드립니다.");

    private HttpStatus status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.builder().reason(reason).code(code).status(status).build();
    }
}