package com.rainbowgon.memberservice.global.redis.repository;

import com.rainbowgon.memberservice.global.redis.dto.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRedisRepository extends CrudRepository<Token, Long> {
}
