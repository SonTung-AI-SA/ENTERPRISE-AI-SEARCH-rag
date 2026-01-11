package com.example.apigateway.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class WebClientConfig {

    @Value("${ai.orchestrator.base-url}")
    private String baseUrl;

    @Value("${ai.orchestrator.timeout-ms}")
    private int timeoutMs;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(
                        new ReactorClientHttpConnector(
                                HttpClient.create()
                                        .responseTimeout(Duration.ofMillis(timeoutMs))
                        )
                )
                .build();
    }
}
