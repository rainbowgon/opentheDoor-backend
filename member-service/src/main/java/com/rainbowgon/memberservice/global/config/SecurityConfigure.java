package com.rainbowgon.memberservice.global.config;

import com.rainbowgon.memberservice.global.security.CustomUserDetailsService;
import com.rainbowgon.memberservice.global.security.JwtTokenProvider;
import com.rainbowgon.memberservice.global.security.filter.ExceptionHandlerFilter;
import com.rainbowgon.memberservice.global.security.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigure {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .cors()
                .and()
                .csrf().disable()

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/members/signup/**").permitAll()
                .antMatchers("/members/login/**").permitAll()
                .antMatchers("/reviews/themes/one/**").permitAll()
                .antMatchers("/members/phone/**").permitAll()
                .antMatchers(("/actuator/**")).permitAll()
                .antMatchers("/clients/**").permitAll() // 서버 간 통신
                .anyRequest().authenticated();

        httpSecurity
                .addFilterBefore(new JwtFilter(jwtTokenProvider, customUserDetailsService),
                                 UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new ExceptionHandlerFilter(), JwtFilter.class);

        return httpSecurity.build();

    }


}
