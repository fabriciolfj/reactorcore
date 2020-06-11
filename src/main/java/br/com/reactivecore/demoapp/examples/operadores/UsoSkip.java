package br.com.reactivecore.demoapp.examples.operadores;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import java.time.Duration;

public class UsoSkip {

    public static void main(String[] args) {
        Flux<Long> fibonacciGenerator = Flux.generate(
                () -> Tuples.<Long, Long>of(0L, 1L),
                (state, sink) -> {
                    if (state.getT1() < 0) {
                        sink.complete();
                    } else {
                        sink.next(state.getT1());
                    }

                    return Tuples.of(state.getT2(), state.getT1() + state.getT2());
                });

        /*
        * ignora os 10 primeiros
        * */
        fibonacciGenerator.skip(10).subscribe(t -> {
            System.out.println(t);
        });

        /*
        * ignora os 10 ultimos
        * */
        fibonacciGenerator.skipLast(10).subscribe(t -> {
            System.out.println(t);
        });

        /*
         * ignora os eventos durante o tempo especificado.
         * */
        fibonacciGenerator.skip(Duration.ofMillis(10)).subscribe(t -> {
            System.out.println(t);
        });

        /*
         * ignora os eventos ate que a condição seja verdadeira
         * */
        fibonacciGenerator.skipUntil(t -> t > 100).subscribe(t -> {
            System.out.println(t);
        });
    }
}
