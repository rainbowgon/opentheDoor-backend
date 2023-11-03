<<<<<<<< HEAD:sender-server/src/main/java/com/rainbowgon/senderserver/global/error/dto/ErrorReason.java
package com.rainbowgon.senderserver.global.error.dto;
========
package com.rainbowgon.searchservice.global.error.dto;

>>>>>>>> f8213dc00867a97502b3f55955962a8328cb899b:search-service/src/main/java/com/rainbowgon/searchservice/global/error/dto/ErrorReason.java

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorReason {

    private final HttpStatus status;
    private final String code;
    private final String reason;
}