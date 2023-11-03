package com.rainbowgon.search.global.response;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import java.util.List;

public class JsonResponse {

    public static ResponseEntity<ResponseWrapper<Nullable>> ok(String message) {
        ResponseWrapper<Nullable> responseWrapper = new ResponseWrapper<>(null, message, null);
        return ResponseEntity.ok(responseWrapper);
    }

    public static <T> ResponseEntity<ResponseWrapper<T>> ok(String message, T data) {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<>(null, message, data);
        return ResponseEntity.ok(responseWrapper);
    }

    public static <T> ResponseEntity<ResponseWrapper<T>> ok(String message, T data, PageInfo pageInfo) {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<>(pageInfo, message, data);
        return ResponseEntity.ok(responseWrapper);
    }

    public static <T> ResponseEntity<ResponseWrapper<List<T>>> ok(String message, Page<T> page) {
        // Page 정보를 추출합니다.
        PageInfo pageInfo = new PageInfo(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );

        // content를 List로 변환합니다.
        List<T> content = page.getContent();

        // Page 정보와 데이터를 포함하는 ResponseWrapper를 생성합니다.
        ResponseWrapper<List<T>> responseWrapper = new ResponseWrapper<>(pageInfo, message, content);

        // ResponseEntity로 감싸서 반환합니다.
        return ResponseEntity.ok(responseWrapper);
    }


    public static ResponseEntity<ResponseWrapper<Nullable>> of(HttpStatus httpStatus, String message) {
        ResponseWrapper<Nullable> responseWrapper = new ResponseWrapper<>(null, message, null);
        return ResponseEntity.status(httpStatus).body(responseWrapper);
    }

    public static <T> ResponseEntity<ResponseWrapper<T>> of(HttpStatus httpStatus, String message, T data) {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<>(null, message, data);
        return ResponseEntity.status(httpStatus).body(responseWrapper);
    }

    public static <T> ResponseEntity<ResponseWrapper<T>> of(HttpStatus httpStatus, String message, T data, PageInfo pageInfo) {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<>(pageInfo, message, data);
        return ResponseEntity.status(httpStatus).body(responseWrapper);
    }

}
