package com.rainbowgon.searchservice.global.response;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import java.util.List;

public class JsonResponse {

    public static ResponseEntity<com.rainbowgon.searchservice.global.response.ResponseWrapper<Nullable>> ok(String message) {
        com.rainbowgon.searchservice.global.response.ResponseWrapper<Nullable> responseWrapper =
                new com.rainbowgon.searchservice.global.response.ResponseWrapper<>(null, message, null);
        return ResponseEntity.ok(responseWrapper);
    }

    public static <T> ResponseEntity<com.rainbowgon.searchservice.global.response.ResponseWrapper<T>> ok(String message, T data) {
        com.rainbowgon.searchservice.global.response.ResponseWrapper<T> responseWrapper =
                new com.rainbowgon.searchservice.global.response.ResponseWrapper<>(null, message, data);
        return ResponseEntity.ok(responseWrapper);
    }

    public static <T> ResponseEntity<com.rainbowgon.searchservice.global.response.ResponseWrapper<T>> ok(String message, T data, com.rainbowgon.searchservice.global.response.PageInfo pageInfo) {
        com.rainbowgon.searchservice.global.response.ResponseWrapper<T> responseWrapper =
                new com.rainbowgon.searchservice.global.response.ResponseWrapper<>(pageInfo, message, data);
        return ResponseEntity.ok(responseWrapper);
    }

    public static <T> ResponseEntity<com.rainbowgon.searchservice.global.response.ResponseWrapper<List<T>>> ok(String message, Page<T> page) {
        // Page 정보를 추출합니다.
        com.rainbowgon.searchservice.global.response.PageInfo pageInfo =
                com.rainbowgon.searchservice.global.response.PageInfo.builder()
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();

        // content를 List로 변환합니다.
        List<T> content = page.getContent();

        // Page 정보와 데이터를 포함하는 ResponseWrapper를 생성합니다.
        com.rainbowgon.searchservice.global.response.ResponseWrapper<List<T>> responseWrapper =
                new com.rainbowgon.searchservice.global.response.ResponseWrapper<>(pageInfo, message,
                                                                                   content);

        // ResponseEntity로 감싸서 반환합니다.
        return ResponseEntity.ok(responseWrapper);
    }


    public static ResponseEntity<com.rainbowgon.searchservice.global.response.ResponseWrapper<Nullable>> of(HttpStatus httpStatus, String message) {
        com.rainbowgon.searchservice.global.response.ResponseWrapper<Nullable> responseWrapper =
                new com.rainbowgon.searchservice.global.response.ResponseWrapper<>(null, message, null);
        return ResponseEntity.status(httpStatus).body(responseWrapper);
    }

    public static <T> ResponseEntity<com.rainbowgon.searchservice.global.response.ResponseWrapper<T>> of(HttpStatus httpStatus, String message, T data) {
        com.rainbowgon.searchservice.global.response.ResponseWrapper<T> responseWrapper =
                new com.rainbowgon.searchservice.global.response.ResponseWrapper<>(null, message, data);
        return ResponseEntity.status(httpStatus).body(responseWrapper);
    }

    public static <T> ResponseEntity<com.rainbowgon.searchservice.global.response.ResponseWrapper<T>> of(HttpStatus httpStatus, String message, T data, com.rainbowgon.searchservice.global.response.PageInfo pageInfo) {
        com.rainbowgon.searchservice.global.response.ResponseWrapper<T> responseWrapper =
                new com.rainbowgon.searchservice.global.response.ResponseWrapper<>(pageInfo, message, data);
        return ResponseEntity.status(httpStatus).body(responseWrapper);
    }

}
