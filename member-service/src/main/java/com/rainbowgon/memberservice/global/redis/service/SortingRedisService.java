package com.rainbowgon.memberservice.global.redis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SortingRedisService implements RedisService {

    @Qualifier("sortingRedisStringTemplate")
    private final RedisTemplate<String, String> sortingRedisStringTemplate;

    @Override
    public Integer keysAll() {
        return sortingRedisStringTemplate.keys("*").size();
    }

    @Override
    public Boolean flushAll() {
        try {
            sortingRedisStringTemplate.getConnectionFactory().getConnection().flushAll();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
