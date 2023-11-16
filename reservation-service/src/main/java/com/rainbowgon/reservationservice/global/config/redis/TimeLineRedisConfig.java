package com.rainbowgon.reservationservice.global.config.redis;

import org.springframework.beans.factory.annotation.Qualifier;
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
public class TimeLineRedisConfig {

    @Value("${spring.redis.timeLine.host}")
    private String host;

    @Value("${spring.redis.timeLine.port}")
    private int port;

    @Primary
    @Bean(name = "timeLineRedisConnectionFactory")
    public RedisConnectionFactory timeLineRedisConnectionFactory() {

        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);

        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean(name = "timeLineRedisStringTemplate")
    public RedisTemplate<String, String> timeLineRedisStringTemplate(
            @Qualifier("timeLineRedisConnectionFactory")
            RedisConnectionFactory timeLineRedisConnectionFactory) {

        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(timeLineRedisConnectionFactory);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        return redisTemplate;
    }

}