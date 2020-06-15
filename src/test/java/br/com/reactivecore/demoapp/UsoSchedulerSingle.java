package br.com.reactivecore.demoapp;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuples;

import java.time.Duration;

public class UsoSchedulerSingle {

    /*
    * Scheduler single executa em um pool de threads de um unico worker,
    * as tarefas são executadas uma a uma, e nenhuma e executada de forma simultânia
    * é ideal para execuções não thread-safe
    * */

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
            System.out.println("Name thread dentro "  + Thread.currentThread().getName());
            return Tuples.of(state.getT2(), state.getT1() + state.getT2());
        });

        fibonnaciGenerator
                .filter(x -> {
                    System.out.println("Executando o filter");
                    return x < 100;
                }).delayElements(Duration.ZERO, Schedulers.single())
                .doOnNext(x -> System.out.println("Next value is " + x))
                .doFinally(x -> System.out.println("Closing"))
                .subscribe(x -> System.out.println("Valor recebido: " + x));

        System.out.println("Name thread fora "  + Thread.currentThread().getName());
        Thread.sleep(500);
    }
}
