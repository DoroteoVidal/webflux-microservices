package com.dorovidal.webfluxdemo.webclient;

import com.dorovidal.webfluxdemo.dto.InputFailedValidationResponse;
import com.dorovidal.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec06ExchangeTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    // exchange es recuperar mas informacion adicional como el codigo de estado Http
    // exchange = retrieve + additional info
    // permite tener mas control y respuesta completa del cliente
    @Test
    public void badRequestTest() {
        Mono<Object> responseMono = this.webClient
                .get()
                .uri("reactive-math/square/{number}/throw", 11)
                .exchangeToMono(this::exchange)
                .doOnNext(System.out::println)
                .doOnError(err -> System.out.println(err.getMessage()));

        StepVerifier.create(responseMono)
                .expectNextCount(1)
                .verifyComplete();
    }

    private Mono<Object> exchange(ClientResponse cr) {
        if(cr.statusCode().value() == 400) {
            return cr.bodyToMono(InputFailedValidationResponse.class);
        } else {
            return cr.bodyToMono(Response.class);
        }
    }
}
