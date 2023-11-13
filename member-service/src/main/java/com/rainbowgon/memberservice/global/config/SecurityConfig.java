package com.rainbowgon.memberservice.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsUtils;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf().disable();

        httpSecurity
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic().disable()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/members/signup/**").permitAll()
                .antMatchers("/members/login/**").permitAll()
                .antMatchers("/reviews/themes/one/**").permitAll()
                .antMatchers("/members/phone/**").permitAll()
                .antMatchers(("/actuator/**")).permitAll()
                .antMatchers("/members/clients/**").permitAll() // 서버 간 통신
                .antMatchers("/bookmarks/clients/**").permitAll() // 서버 간 통신
                .antMatchers("/profiles/clients/**").permitAll() // 서버 간 통신
                .antMatchers("/reviews/clients/**").permitAll() // 서버 간 통신
                .antMatchers("/members/test/**").permitAll() // 서버 간 통신 테스트
                .anyRequest().authenticated();

        return httpSecurity.build();
    }


}