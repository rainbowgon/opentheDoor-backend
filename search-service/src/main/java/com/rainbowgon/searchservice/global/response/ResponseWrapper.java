package com.rainbowgon.searchservice.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseWrapper<T> {

    private com.rainbowgon.searchservice.global.response.PageInfo pageInfo;
    private String message;
    private T data;
}
