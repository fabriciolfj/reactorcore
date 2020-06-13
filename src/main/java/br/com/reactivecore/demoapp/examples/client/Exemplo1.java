package br.com.reactivecore.demoapp.examples.client;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class Exemplo1 {

    public static void main(String[] args) {
        var client = WebClient.create("http://localhost:8080");
        Flux<Long> dados = client.get().uri("/fibonacci")
                .retrieve()
                .bodyToFlux(Long.class)
                .limitRequest(10L);

        dados.subscribe(System.out::println);
    }
}
