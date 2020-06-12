package br.com.reactivecore.demoapp.examples.operadores;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

public class UsoSample {

    /***
     *  Um forma de estratégia de backpressure
     *  similar ao buffer, mas melhor, pois gerencia melhor a memória
     *  fica ouindo durante um tempo e pega o ultimo evento
     */

    public static void main(String[] args) throws InterruptedException {
        Flux<Long> fibonacciGenerator = Flux.generate(() ->
                Tuples.of(0L, 1L),
                (state, sink) -> {
                    if (state.getT1() < 0) {
                        sink.complete();
                    } else {
                       sink.next(state.getT1());
                    }

                    return Tuples.of(state.getT2(), state.getT1() + state.getT2());
                });

        CountDownLatch latch = new CountDownLatch(10);
        fibonacciGenerator.delayElements(Duration.ofMillis(100L))
                .sample(Duration.ofSeconds(1))
                .subscribe(x -> System.out.println(x), e -> latch.countDown(), () -> latch.countDown());
        latch.await();
    }
}
