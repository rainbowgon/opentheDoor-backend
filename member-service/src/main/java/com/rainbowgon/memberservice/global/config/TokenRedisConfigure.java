package com.rainbowgon.memberservice.global.config;

import com.rainbowgon.memberservice.global.security.dto.Token;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class TokenRedisConfigure {

    @Value("${spring.redis.token.host}")
    private String host;

    @Value("${spring.redis.token.port}")
    private int port;

//    @Value("${redis.password}")
//    private String password;

    @Primary
    @Bean(name = "tokenRedisConnectionFactory")
    public RedisConnectionFactory tokenRedisConnectionFactory() {

        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
//        redisStandaloneConfiguration.setPassword(password);

        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean(name = "tokenRedisHashTemplate")
    public RedisTemplate<Long, Token> tokenRedisHashTemplate(
            @Qualifier("tokenRedisConnectionFactory") RedisConnectionFactory tokenRedisConnectionFactory) {

        RedisTemplate<Long, Token> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(tokenRedisConnectionFactory);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Token.class));

        return redisTemplate;
    }

}
