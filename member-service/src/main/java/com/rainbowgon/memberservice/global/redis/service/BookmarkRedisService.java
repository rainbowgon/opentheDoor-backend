package com.rainbowgon.memberservice.global.redis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookmarkRedisService implements RedisService {

    @Qualifier("bookmarkRedisStringTemplate")
    private final RedisTemplate<String, String> bookmarkRedisStringTemplate;


    @Override
    public Integer keysAll() {
        return bookmarkRedisStringTemplate.keys("*").size();
    }

    @Override
    public Boolean flushAll() {
        try {
            bookmarkRedisStringTemplate.getConnectionFactory().getConnection().flushAll();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
