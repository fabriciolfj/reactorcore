package br.com.reactivecore.demoapp;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UsoSchedulerFromExecutor {


    /*
     * Este nao deve ser favorecido acima dos anteriores, pois deve ser gerenciado manualmente.
     */

    @Test
    public void testReactor() throws Exception {
        Flux<Long> fibonnaciGenerator = Flux.generate(() ->
                Tuples.of(0L, 1L), (state, sink) -> {
            if (state.getT1() < 0) {
                sink.complete();
            } else {
                sink.next(state.getT1());
            }
            System.out.println("Gerando next of " + state.getT2());
            return Tuples.of(state.getT2(), state.getT1() + state.getT2());
        });

        ExecutorService executor = Executors.newSingleThreadExecutor();

        fibonnaciGenerator
                .filter(x -> {
                    System.out.println("Executando o filter");
                    return x < 100;
                }).delayElements(Duration.ZERO, Schedulers.fromExecutor(executor))
                .window(10)
                .doOnNext(x -> System.out.println("Next value is " + x))
                .doFinally(x -> System.out.println("Closing"))
                .subscribe(x -> System.out.println("Valor recebido: " + x.blockFirst()));
        Thread.sleep(500);
        System.out.println("is shutdown ? " + executor.isShutdown());
    }
}
