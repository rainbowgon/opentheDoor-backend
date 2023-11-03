<<<<<<<< HEAD:sender-server/src/main/java/com/rainbowgon/senderserver/global/error/exception/CustomException.java
package com.rainbowgon.senderserver.global.error.exception;

import com.rainbowgon.senderserver.global.error.dto.ErrorReason;
import com.rainbowgon.senderserver.global.error.errorCode.BaseErrorCode;
========
package com.rainbowgon.searchservice.global.error.exception;

import com.rainbowgon.searchservice.global.error.dto.ErrorReason;
import com.rainbowgon.searchservice.global.error.errorCode.BaseErrorCode;
>>>>>>>> f8213dc00867a97502b3f55955962a8328cb899b:search-service/src/main/java/com/rainbowgon/searchservice/global/error/exception/CustomException.java
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {

    private BaseErrorCode errorCode;

    public ErrorReason getErrorReason() {
        return this.errorCode.getErrorReason();
    }
}
