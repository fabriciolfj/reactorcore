package br.com.reactivecore.demoapp;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuples;

import java.time.Duration;

public class UsoSchedulerElastic {


    /*
     * Scheduler elastic executa o trabalho em um pool de varios workes.
     * cada um pode executar tarefas longas e de bloqueio
     * o worker volta ao pool, quando a execução termina.
     * a também o tempo ocosio de cada trabalhador
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
            return Tuples.of(state.getT2(), state.getT1() + state.getT2());
        });

        fibonnaciGenerator
                .filter(x -> {
                    System.out.println("Executando o filter");
                    return x < 100;
                }).delayElements(Duration.ZERO, Schedulers.elastic())
                .window(10)
                .doOnNext(x -> System.out.println("Next value is " + x))
                .doFinally(x -> System.out.println("Closing"))
                .subscribe(x -> System.out.println("Valor recebido: " + x.blockFirst()));
        Thread.sleep(500);
    }
}
