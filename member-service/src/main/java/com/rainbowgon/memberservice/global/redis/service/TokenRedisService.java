package com.rainbowgon.memberservice.global.redis.service;

import com.rainbowgon.memberservice.global.redis.dto.Token;
import com.rainbowgon.memberservice.global.redis.repository.TokenRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenRedisService implements RedisService {

    @Qualifier("tokenRedisHashTemplate")
    private final RedisTemplate<Long, Token> tokenRedisHashTemplate;
    private final TokenRedisRepository tokenRedisRepository;


    @Override
    public Integer keysAll() {
        List<Token> tokenList = (List<Token>) tokenRedisRepository.findAll();
        return tokenList.size();
    }

    @Override
    public Boolean flushAll() {
        try {
            tokenRedisHashTemplate.getConnectionFactory().getConnection().flushAll();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
