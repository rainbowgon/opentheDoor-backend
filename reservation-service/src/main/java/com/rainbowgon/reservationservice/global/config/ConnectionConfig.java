package com.rainbowgon.reservationservice.global.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@Getter
public class ConnectionConfig {

    @Value("${spring.reservation.target-url}")
    private String reservingServerUrl;

    @Value("${spring.reservation.on-off}")
    private String onOff;

    @Value("${spring.reservation.off-return-value}")
    private String offReturnValue;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
