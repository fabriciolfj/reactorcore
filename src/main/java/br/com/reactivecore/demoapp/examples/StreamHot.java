package br.com.reactivecore.demoapp.examples;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import static java.time.Duration.ofSeconds;

public class StreamHot {

    public static void main(String[] args) {
        ConnectableFlux<Object> publish = Flux.create(fluxSink -> {
            while(true) {
                fluxSink.next(System.currentTimeMillis());
            }
        }).sample(ofSeconds(2))
                .publish();

        publish.subscribe(System.out::println);
        publish.subscribe(System.out::println);
        publish.connect();
    }
}
