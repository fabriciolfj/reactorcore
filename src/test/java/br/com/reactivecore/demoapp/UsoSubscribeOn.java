package br.com.reactivecore.demoapp;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuples;

public class UsoSubscribeOn {

    /*
    * Subscribeon intercepta eventos de um publisher da cadeia de execução, e envia para um scheduler diferente a cadeia completa
    * obs: ele altera a cadeia completa, diferente do publisher on que so altera a execução de uma cadeia
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
                })
                .doOnNext(x -> System.out.println("Next value is " + x))
                .doFinally(x -> System.out.println("Closing"))
                .subscribeOn(Schedulers.elastic())
                .subscribe(x -> System.out.println("Valor recebido: " + x));

        System.out.println("Name thread fora "  + Thread.currentThread().getName());
        Thread.sleep(500);
    }
}
