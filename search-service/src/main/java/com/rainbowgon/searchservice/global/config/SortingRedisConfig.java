package com.rainbowgon.searchservice.global.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class SortingRedisConfig {

    @Value("${spring.redis.sorting.host}")
    private String host;

    @Value("${spring.redis.sorting.port}")
    private int port;


    // Redis 저장소와 연결
    @Bean(name = "sortingRedisConnectionFactory")
    public RedisConnectionFactory sortingRedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);

        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    // RedisTemplate bean 생성
    @Bean(name = "sortingRedisStringTemplate")
    public RedisTemplate<String, String> sortingRedisStringTemplate(
            @Qualifier("sortingRedisConnectionFactory")
            RedisConnectionFactory sortingRedisConnectionFactory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(sortingRedisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

}