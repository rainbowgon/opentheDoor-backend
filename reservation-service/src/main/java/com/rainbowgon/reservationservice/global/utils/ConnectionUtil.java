package com.rainbowgon.reservationservice.global.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class ConnectionUtil {

    public static <T> HttpEntity<T> createRequestBody(T requestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(requestDto, headers);
    }


}
