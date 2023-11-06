package com.rainbowgon.searchservice.global.utils;

public class RedisKeyBuilder {

    public static String buildKey(String sortBy, String keyword) {
        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(sortBy).append(":").append(keyword);
        return keyBuilder.toString();
    }

}
