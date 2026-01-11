package com.example.apigateway.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class QueryController {

    private final WebClient webClient;

    public QueryController(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://ai-orchestrator:8000").build();
    }

    @PostMapping("/query")
    public Mono<ResponseEntity<String>> query(@RequestBody Map<String, String> body) {

        String requestId = UUID.randomUUID().toString();
        body.put("request_id", requestId);

        long start = System.currentTimeMillis();

        return webClient.post()
                .uri("/internal/query")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .map(resp -> {
                    long latency = System.currentTimeMillis() - start;
                    System.out.println("[JAVA] request_id=" + requestId + " latency=" + latency);
                    return ResponseEntity.ok(resp);
                });
    }
}
  