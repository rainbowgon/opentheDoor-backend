package com.rainbowgon.searchservice.global.config;

import com.rainbowgon.searchservice.domain.theme.model.Theme;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class CacheRedisConfig {

    @Value("${spring.data.redis.cache.host}")
    private String host;
    @Value("${spring.data.redis.cache.port}")
    private int port;

//    @Value("${spring.data.redis.password}")
//    private String password;

    // Redis 저장소와 연결
    @Primary
    @Bean(name = "cacheRedisConnectionFactory")
    public RedisConnectionFactory cacheRedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
//        redis1StandaloneConfiguration.setPassword(password);
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }


    // RedisTemplate bean 생성
    @Bean(name = "cacheRedisStringTemplate")
    public RedisTemplate<String, String> cacheRedisStringTemplate(
            @Qualifier("cacheRedisConnectionFactory") RedisConnectionFactory cacheRedisConnectionFactory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(cacheRedisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
        return redisTemplate;
    }

    @Bean(name = "cacheRedisDoubleTemplate")
    public RedisTemplate<String, Double> cacheRedisDoubleTemplate(
            RedisConnectionFactory cacheRedisConnectionFactory) {
        RedisTemplate<String, Double> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(cacheRedisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Double.class));
        return redisTemplate;
    }

    @Bean(name = "cacheRedisFloatTemplate")
    public RedisTemplate<String, Float> cacheRedisFloatTemplate(
            RedisConnectionFactory cacheRedisConnectionFactory) {
        RedisTemplate<String, Float> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(cacheRedisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Float.class));
        return redisTemplate;
    }

    @Bean(name = "cacheRedisThemeTemplate")
    public RedisTemplate<String, Theme> cacheRedisThemeTemplate(
            RedisConnectionFactory cacheConnectionFactory) {
        RedisTemplate<String, Theme> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(cacheConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Theme.class));
        return redisTemplate;
    }

    // 레디스 캐시
    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redis1ConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext
                                           .SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext
                                             .SerializationPair.fromSerializer(
                                new GenericJackson2JsonRedisSerializer()));

        return RedisCacheManager
                .RedisCacheManagerBuilder
                .fromConnectionFactory(redis1ConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();

    }

}
