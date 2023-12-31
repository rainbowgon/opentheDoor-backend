package com.rainbowgon.memberservice.global.error.errorCode;

import com.rainbowgon.memberservice.global.error.dto.ErrorReason;
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
    MEMBER_BAD_PHONE_NUMBER(BAD_REQUEST, "MEMBER-400-1", "올바른 전화번호 형식이 아닙니다."),

    /* Auth */
    AUTH_NO_AUTHORIZATION(FORBIDDEN, "AUTH-403-1", "Request header에 Authorization이 존재하지 않습니다."),
    AUTH_TOKEN_EXPIRED(UNAUTHORIZED, "AUTH-401-1", "만료된 토큰입니다."),
    AUTH_KAKAO_TOKEN_FAILURE(INTERNAL_SERVER_ERROR, "AUTH-500-1", "카카오 Access Token을 받아오지 못했습니다."),
    AUTH_KAKAO_PROFILE_FAILURE(INTERNAL_SERVER_ERROR, "AUTH-500-2", "카카오 프로필을 받아오지 못했습니다."),

    /* Profile */
    PROFILE_NOT_FOUND(NOT_FOUND, "PROFILE-404-1", "해당 프로필을 찾을 수 없습니다."),
    PROFILE_NICKNAME_DUPLICATION(CONFLICT, "PROFILE-409-1", "이미 존재하는 닉네임입니다."),
    PROFILE_UNAUTHORIZED(UNAUTHORIZED, "PROFILE-401-1", "해당 프로필에 접근 권한이 없습니다."),

    /* Bookmark */
    BOOKMARK_NOT_FOUND(NOT_FOUND, "BOOKMARK-404-1", "해당 북마크를 찾을 수 없습니다."),
    BOOKMARK_UNAUTHORIZED(UNAUTHORIZED, "BOOKMARK-401-1", "해당 북마크에 접근 권한이 없습니다."),

    /* Review */
    REVIEW_NOT_FOUND(NOT_FOUND, "REVIEW-404-1", "해당 리뷰를 찾을 수 없습니다."),
    REVIEW_UNAUTHORIZED(UNAUTHORIZED, "REVIEW-401-1", "해당 리뷰에 접근 권한이 없습니다."),

    /* Redis */
    REDIS_SERVER_ERROR(INTERNAL_SERVER_ERROR, "GLOBAL-500-2", "Redis에서 오류가 발생했습니다."),
    AWS_S3_SERVER_ERROR(INTERNAL_SERVER_ERROR, "GLOBAL-500-3", "AWS S3에서 오류가 발생했습니다."),

    CUSTOM_INTERNAL_SERVER_ERROR(INTERNAL_SERVER_ERROR, "GLOBAL-500-1", "서버 오류. 관리자에게 문의 부탁드립니다.");

    private HttpStatus status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.builder().reason(reason).code(code).status(status).build();
    }
}