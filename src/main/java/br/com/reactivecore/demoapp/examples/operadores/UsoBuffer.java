package br.com.reactivecore.demoapp.examples.operadores;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class UsoBuffer {

    /***
     *  Um forma de estratégia de backpressure
     */

    public static void main(String[] args) {
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

        /*fibonacciGenerator.take(100)
                .buffer() // vai propagar lista em 10 em 10
                .subscribe(x -> System.out.println(x));

        fibonacciGenerator.take(100)
                .buffer(2,5) // vai emitir uma lista 2 em 2, a partir do 5 elemento da lista
                .subscribe(x -> System.out.println(x));*/

        /*fibonacciGenerator.take(100)
                .bufferWhile(i -> i%2 ==0) // vai o que corresponde a condição, mas ele gera uma lista para cada resultado
                .subscribe(x -> System.out.println(x));*/

        /*fibonacciGenerator.take(100)
                .bufferUntil(i -> i%2 ==0) // vai o que corresponde a condição, mas ele gera uma lista
                .subscribe(x -> System.out.println(x));*/

        /*fibonacciGenerator.take(100)
                .buffer(Duration.ofNanos(10)) // vai propagar lista durante esse tempo
                .subscribe(x -> System.out.println(x));*/

        fibonacciGenerator.take(100)
                .buffer(5, HashSet::new) // fazendo uma coleta, de 5 elementos em um hasset
                .subscribe(x -> System.out.println(x));
    }
}
