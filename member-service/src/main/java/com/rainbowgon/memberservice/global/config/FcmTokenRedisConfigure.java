package com.rainbowgon.memberservice.global.config;

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
public class FcmTokenRedisConfigure {

    @Value("${spring.redis.fcm-token.host}")
    private String host;

    @Value("${spring.redis.fcm-token.port}")
    private int port;

    @Value("${redis.password}")
    private String password;

    @Bean(name = "fcmTokenRedisConnectionFactory")
    public RedisConnectionFactory fcmTokenRedisConnectionFactory() {

        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(password);

        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean(name = "fcmTokenRedisStringTemplate")
    public RedisTemplate<String, String> fcmTokenRedisStringTemplate(
            @Qualifier("fcmTokenRedisConnectionFactory") RedisConnectionFactory fcmTokenRedisConnectionFactory) {

        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(fcmTokenRedisConnectionFactory);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        return redisTemplate;
    }

}
