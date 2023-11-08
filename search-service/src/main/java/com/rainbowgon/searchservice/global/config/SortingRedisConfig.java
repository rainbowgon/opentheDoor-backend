package com.rainbowgon.searchservice.global.config;

import com.rainbowgon.searchservice.domain.theme.model.Theme;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
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
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
        return redisTemplate;
    }

    @Bean(name = "sortingRedisDoubleTemplate")
    public RedisTemplate<String, Double> sortingRedisDoubleTemplate(
            @Qualifier("sortingRedisConnectionFactory")
            RedisConnectionFactory sortingRedisConnectionFactory) {
        RedisTemplate<String, Double> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(sortingRedisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Double.class));
        return redisTemplate;
    }

    @Bean(name = "sortingRedisFloatTemplate")
    public RedisTemplate<String, Float> sortingRedisFloatTemplate(
            @Qualifier("sortingRedisConnectionFactory")
            RedisConnectionFactory sortingRedisConnectionFactory) {
        RedisTemplate<String, Float> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(sortingRedisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Float.class));
        return redisTemplate;
    }

    @Bean(name = "sortingRedisThemeTemplate")
    public RedisTemplate<String, Theme> sortingRedisThemeTemplate(
            @Qualifier("sortingRedisConnectionFactory")
            RedisConnectionFactory sortingConnectionFactory) {
        RedisTemplate<String, Theme> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(sortingConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Theme.class));
        return redisTemplate;
    }

}