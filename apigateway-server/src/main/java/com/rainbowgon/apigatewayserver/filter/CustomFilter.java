package com.rainbowgon.apigatewayserver.filter;

import com.rainbowgon.apigatewayserver.redis.RefreshTokenRedisRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    public static class Config { }

    public CustomFilter(RefreshTokenRedisRepository refreshTokenRedisRepository) {
        super(Config.class);
        this.refreshTokenRedisRepository = refreshTokenRedisRepository;
    }

    @Override
    public GatewayFilter apply(Config config) {

        // custom pre filter
        return (exchange, chain) -> {

            // request id 로그로 출력
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            log.info("Custom Filter is start ... request id = {}", request.getId());

            // 토큰 없을 때
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "토큰이 없습니다.", HttpStatus.UNAUTHORIZED);
            }



            // global post filter
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if (config.isPostLogger()) {
                    log.info("Global Filter is end ... status code = {}", response.getStatusCode());
                }
            }));
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        log.error("onError");
        return response.setComplete();
    }

}
