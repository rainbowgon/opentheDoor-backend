package com.rainbowgon.searchservice.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import javax.validation.constraints.NotNull;
import java.time.Duration;

@Configuration
@EnableElasticsearchRepositories
public class EsClientConfig extends ElasticsearchConfiguration {

    // 연결 타임아웃 및 소켓 타임아웃 설정을 위한 상수
    private static final long CONNECTION_TIMEOUT = 60000; // 예: 10초
    private static final long SOCKET_TIMEOUT = 60000; // 예: 60초
    @Value("${elasticsearch.host}")
    private String host;
    @Value("${elasticsearch.port}")
    private String port;

    @NotNull
    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(host + ":" + port)
                // 연결 타임아웃과 소켓 타임아웃 설정
                .withConnectTimeout(Duration.ofMillis(CONNECTION_TIMEOUT))
                .withSocketTimeout(Duration.ofMillis(SOCKET_TIMEOUT))
                .withHttpClientConfigurer(clientBuilder -> {
                    clientBuilder.setKeepAliveStrategy((httpResponse, httpContext) -> 1000 * 60);
                    return clientBuilder;
                })
//                .withBasicAuth(username, password) // 필요한 경우 주석 해제
                .build();
    }
}
