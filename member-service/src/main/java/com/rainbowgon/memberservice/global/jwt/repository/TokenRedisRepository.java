package com.rainbowgon.memberservice.global.jwt.repository;

import com.rainbowgon.memberservice.global.jwt.dto.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRedisRepository extends CrudRepository<Token, Long> {
}
