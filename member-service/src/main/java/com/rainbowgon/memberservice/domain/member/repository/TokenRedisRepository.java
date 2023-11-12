package com.rainbowgon.memberservice.domain.member.repository;

import com.rainbowgon.memberservice.domain.member.entity.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRedisRepository extends CrudRepository<Token, Long> {
}
