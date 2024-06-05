package com.dorovidal.webfluxdemo.webtestclient;

import com.dorovidal.webfluxdemo.config.RequestHandler;
import com.dorovidal.webfluxdemo.config.RouterConfig;
import com.dorovidal.webfluxdemo.dto.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerResponse;

@WebFluxTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = RouterConfig.class)
public class Lec05RouterFunctiontTest {

    private WebTestClient client;

    @Autowired
    private ApplicationContext ctx;

    @MockBean
    private RequestHandler handler;

    @BeforeAll
    public void setClient() {
        client = WebTestClient.bindToApplicationContext(ctx).build();
    }

    @Test
    public void test() {
        Mockito.when(handler.squareHandler(Mockito.any()))
                .thenReturn(ServerResponse.ok().bodyValue(new Response(225)));

        client
                .get()
                .uri("/router/square/{input}", 15)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(r -> Assertions.assertThat(r.getOutput()).isEqualTo(225));
    }
}
