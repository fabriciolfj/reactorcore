package br.com.reactivecore.demoapp;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuples;

import java.time.Duration;

public class UsoPublishOn {

    /*
    * Publish on intercepta eventos de um publisher em um ponto configurado na cadeia de execução e os envia para
    * um scheduler diferente, ou seja, estou na thread principal executando, e aquela corrente de dados vai para outro contexto
    * ou seja, para o scheduler configurado
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
                .publishOn(Schedulers.single())
                .filter(x -> {
                    System.out.println("Executando o filter");
                    return x < 100;
                })
                .doOnNext(x -> System.out.println("Next value is " + x))
                .doFinally(x -> System.out.println("Closing"))
                .subscribe(x -> System.out.println("Valor recebido: " + x));

        System.out.println("Name thread fora "  + Thread.currentThread().getName());
        Thread.sleep(500);
    }
}
