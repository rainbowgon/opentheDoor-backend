package com.rainbowgon.memberservice.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseWrapper<T> {

    private PageInfo pageInfo;
    private String message;
    private T data;
}