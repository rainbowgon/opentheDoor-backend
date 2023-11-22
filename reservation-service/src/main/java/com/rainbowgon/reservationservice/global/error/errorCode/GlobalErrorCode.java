package com.rainbowgon.reservationservice.global.error.errorCode;

import com.rainbowgon.reservationservice.global.error.dto.ErrorReason;
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
    EXAMPLE_NOT_FOUND(NOT_FOUND, "EXAMPLE_404_1", "예시를 찾을 수 없는 오류입니다."),

    CUSTOM_INTERNAL_SERVER_ERROR(INTERNAL_SERVER_ERROR, "GLOBAL-500-1", "서버 오류. 관리자에게 문의 부탁드립니다."),
    BOOKER_INFO_INVALID(FORBIDDEN, "RESERVATION-403-1", "회원의 정보와 예약자의 정보가 일치하지 않습니다."),
    DATETIME_INVALID(HttpStatus.FORBIDDEN, "RESERVATION-403-2", "날짜, 시간이 형식에 맞지 않습니다."),
    RESERVATION_NOT_FOUND(NOT_FOUND, "RESERVATION-404-1", "해당 예약 정보가 없습니다."),
    WAITING_ALREADY_EXISTS(FORBIDDEN, "WAITING-403-1", "이미 예약 대기 신청을 했습니다."),
    WAITING_HISTORY_NOT_FOUND(NOT_FOUND, "WAITING-404-1", "예약 대기 신청 내역이 없습니다."),
    TIMELINE_NOT_FOUND(NOT_FOUND, "TIMELINE-404-1", "해당 테마의 시간 정보가 없습니다.");

    private HttpStatus status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.builder().reason(reason).code(code).status(status).build();
    }
}
