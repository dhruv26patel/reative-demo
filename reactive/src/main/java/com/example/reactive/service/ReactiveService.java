package com.example.reactive.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ReactiveService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private RestTemplate restTemplate;

    public Mono<String> getData() {

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-App-Token", System.getenv("X-App-Token"));
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<String> entityReq = new HttpEntity<>(headers);

        long startTime1 = System.nanoTime();

        ResponseEntity<String> dataRest = restTemplate.exchange("https://data.cityofchicago.org/resource/yhhz-zm2v.json", HttpMethod.GET, entityReq, String.class);

        long endTime1   = System.nanoTime();
        long totalTime1 = endTime1 - startTime1;

        System.out.println("Synchronous Call: " + totalTime1);

        long startTime2 = System.nanoTime();

        Mono<String> dataRestWebCleint = webClientBuilder
                .build()
                .get()
                .uri("https://data.cityofchicago.org/resource/yhhz-zm2v.json")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("X-App-Token", System.getenv("X-App-Token"))
                .retrieve()
                .bodyToMono(String.class);

        long endTime2   = System.nanoTime();
        long totalTime2 = endTime2 - startTime2;

        System.out.println("Asynchronous Call: " + totalTime2);

        return dataRestWebCleint;
    }
}
