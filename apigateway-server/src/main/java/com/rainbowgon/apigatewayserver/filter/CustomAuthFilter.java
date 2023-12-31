package com.rainbowgon.apigatewayserver.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rainbowgon.apigatewayserver.error.dto.ErrorReason;
import com.rainbowgon.apigatewayserver.error.dto.ErrorResponse;
import com.rainbowgon.apigatewayserver.error.errorCode.BaseErrorCode;
import com.rainbowgon.apigatewayserver.error.exception.ExpiredTokenException;
import com.rainbowgon.apigatewayserver.error.exception.InvalidTokenException;
import com.rainbowgon.apigatewayserver.error.exception.NoAuthorizationException;
import com.rainbowgon.apigatewayserver.error.exception._CustomException;
import com.rainbowgon.apigatewayserver.redis.Token;
import com.rainbowgon.apigatewayserver.redis.TokenRedisRepository;
import com.rainbowgon.apigatewayserver.security.JwtTokenDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@Component
public class CustomAuthFilter extends AbstractGatewayFilterFactory<CustomAuthFilter.Config> {

    private final JwtTokenDecoder jwtTokenDecoder;
    private final TokenRedisRepository tokenRedisRepository;
    private final ObjectMapper objectMapper;

    public static class Config {}

    public CustomAuthFilter(JwtTokenDecoder jwtTokenDecoder, TokenRedisRepository tokenRedisRepository,
                            ObjectMapper objectMapper) {
        super(Config.class);
        this.jwtTokenDecoder = jwtTokenDecoder;
        this.tokenRedisRepository = tokenRedisRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public GatewayFilter apply(Config config) {

        // custom pre filter
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            // jwt token 유무 확인
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, NoAuthorizationException.EXCEPTION);
            }

            String accessToken = getToken(request);
            if (accessToken == null) {
                return onError(exchange, NoAuthorizationException.EXCEPTION);
            }

            log.info("Custom Auth Filter ... accessToken = {}", accessToken);

            // token 유효성 검사
            if (jwtTokenDecoder.validateToken(accessToken)) {
                return onError(exchange, ExpiredTokenException.EXCEPTION);
            }

            // 토큰에서 profileId 뽑아내기
            Long profileId = jwtTokenDecoder.getProfileId(accessToken);
            log.info("Custom Auth Filter ... profileId = {}", profileId);

            // redis에서 profileId로 memberId 가져오기
            Optional<Token> tokenDto = tokenRedisRepository.findById(profileId);
            if (tokenDto.isEmpty()) {
                return onError(exchange, InvalidTokenException.EXCEPTION);
            }
            String memberId = tokenDto.get().getMemberId();
            log.info("Custom Auth Filter ... memberId = {}", memberId);

            // 헤더에 memberId 추가
            ServerHttpRequest exchangeRequest = exchange.getRequest().mutate().header("memberId", memberId).build();

            return chain.filter(exchange.mutate().request(exchangeRequest).build());
        };
    }

    /**
     * error를 공통 response format으로 반환
     */
    private Mono<Void> onError(ServerWebExchange exchange, _CustomException customException) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // custom exception 가져오기
        BaseErrorCode code = customException.getErrorCode();
        ErrorReason errorReason = code.getErrorReason();
        ErrorResponse errorResponse = ErrorResponse.of(errorReason, request.getPath().toString());

        // error response 반환
        response.setStatusCode(errorReason.getStatus());

        String error = errorReason.getReason();
        log.error("API gateway custom exception ... reason = {}", errorReason.getReason());

        try {
            error = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(errorResponse);
        } catch (JsonProcessingException e) {
            log.error("API gateway JsonProcessingException = {}", e.getMessage());
        }

        byte[] bytes = error.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);

        return response.writeWith(Flux.just(buffer));
    }

    /**
     * request header에서 jwt token 꺼내기
     */
    private String getToken(ServerHttpRequest request) {

        String headerAuth = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }

}
