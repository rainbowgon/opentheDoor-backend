package com.rainbowgon.memberservice.global.error;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        int status = response.status();
        switch (status) {
            case 400:
                break;
            case 500:
                if (methodKey.contains("getBookmarkTheme")) {
                    return new ResponseStatusException(HttpStatus.valueOf(status), "search server error");
                }
            default:
                return new Exception(response.reason());
        }
        return null;
    }
}