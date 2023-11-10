package com.rainbowgon.apigatewayserver.redis;

import org.springframework.data.repository.CrudRepository;

public interface TokenRedisRepository extends CrudRepository<Token, String> {
}
