package com.rainbowgon.memberservice.global.security.repository;

import com.rainbowgon.memberservice.global.security.dto.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRedisRepository extends CrudRepository<Token, Long> {
}
