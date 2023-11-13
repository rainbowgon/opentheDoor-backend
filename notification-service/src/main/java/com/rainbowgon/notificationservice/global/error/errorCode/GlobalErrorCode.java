package com.rainbowgon.notificationservice.global.error.errorCode;

import com.rainbowgon.notificationservice.global.error.dto.ErrorReason;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode implements BaseErrorCode {

    /**
     * 예시
     */
    EXAMPLE_NOT_FOUND(NOT_FOUND, "EXAMPLE_404_1", "예시를 찾을 수 없는 오류입니다."),
    CUSTOM_INTERNAL_SERVER_ERROR(INTERNAL_SERVER_ERROR, "GLOBAL_500_1", "서버 오류. 관리자에게 문의 부탁드립니다."),

    /*kafka*/
    JSON_TOSTRING_FAIL(INTERNAL_SERVER_ERROR, "KAFKA-500-1", "KafkaProducer JSON to String 변환 오류입니다."),

    /*redis*/
    REDIS_KEY_NOT_FOUND(NOT_FOUND, "REDIS-404-1", "Redis Key에 해당하는 데이터를 찾지 못했습니다.");

    private HttpStatus status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.builder().reason(reason).code(code).status(status).build();
    }
}
