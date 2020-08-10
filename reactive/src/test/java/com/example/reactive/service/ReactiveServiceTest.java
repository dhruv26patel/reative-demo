package com.example.reactive.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
@DirtiesContext
class ReactiveServiceTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testWebClient() {

        Flux<String> intergerFlux = webTestClient.get().uri("/web-client-data")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(String.class)
                .getResponseBody();

        StepVerifier.create(intergerFlux)
                .expectSubscription()
                .expectNextMatches(data -> !data.isEmpty());
    }

}