package com.rainbowgon.memberservice.global.redis.service;

public interface RedisService {

    /**
     * redis 전체 데이터 수 조회
     */
    Integer keysAll();

    /**
     * redis 초기화
     */
    Boolean flushAll();
}
