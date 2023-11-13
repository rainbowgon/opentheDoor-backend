package com.rainbowgon.apigatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange()
                .anyExchange().permitAll()
                .and()
                .securityContextRepository(new StatelessWebSessionSecurityContextRepository())
                .cors().disable()
                .csrf().disable()
                .httpBasic().disable()
                .headers()
                .contentSecurityPolicy("script-src 'self'");

        return http.build();
    }

    private static class StatelessWebSessionSecurityContextRepository implements ServerSecurityContextRepository {

        private static final Mono<SecurityContext> EMPTY_CONTEXT = Mono.empty();

        @Override
        public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
            return Mono.empty();
        }

        @Override
        public Mono<SecurityContext> load(ServerWebExchange exchange) {
            return EMPTY_CONTEXT;
        }

    }
}
