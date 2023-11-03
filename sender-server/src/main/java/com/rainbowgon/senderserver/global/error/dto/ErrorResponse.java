<<<<<<<< HEAD:sender-server/src/main/java/com/rainbowgon/senderserver/global/error/dto/ErrorResponse.java
package com.rainbowgon.senderserver.global.error.dto;
========
package com.rainbowgon.memberservice.global.error.dto;
>>>>>>>> f8213dc00867a97502b3f55955962a8328cb899b:member-service/src/main/java/com/rainbowgon/memberservice/global/error/dto/ErrorResponse.java

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {

    private final HttpStatus status;
    private final String code;
    private final String reason;
    private final LocalDateTime timeStamp;
    private final String path;

    public static ErrorResponse of(ErrorReason errorReason, String path) {
        return ErrorResponse.builder()
                .status(errorReason.getStatus())
                .code(errorReason.getCode())
                .reason(errorReason.getReason())
                .timeStamp(LocalDateTime.now())
                .path(path).build();
    }

    public static ErrorResponse of(HttpStatus status, String code, String reason, String path) {
        return ErrorResponse.builder()
                .status(status)
                .code(code)
                .reason(reason)
                .timeStamp(LocalDateTime.now())
                .path(path).build();
    }

}