package com.rainbowgon.memberservice.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class BookmarkRedisConfigure {

    @Value("${spring.redis.bookmark.host}")
    private String host;

    @Value("${spring.redis.bookmark.port}")
    private int port;

    @Value("${redis.password}")
    private String password;

    // Redis 저장소와 연결
    @Primary
    @Bean(name = "bookmarkRedisConnectionFactory")
    public RedisConnectionFactory bookmarkRedisConnectionFactory() {

        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(password);

        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    // RedisTemplate bean 생성
    @Bean(name = "bookmarkRedisStringTemplate")
    public RedisTemplate<String, String> bookmarkRedisStringTemplate(RedisConnectionFactory bookmarkRedisConnectionFactory) {

        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(bookmarkRedisConnectionFactory);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        return redisTemplate;
    }

}
