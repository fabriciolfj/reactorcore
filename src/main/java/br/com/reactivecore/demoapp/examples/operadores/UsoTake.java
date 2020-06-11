package br.com.reactivecore.demoapp.examples.operadores;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

public class UsoTake {

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
        * pega os 10 primeiros
        * */
        fibonacciGenerator.take(10).subscribe(t -> {
            System.out.println(t);
        });

        /*
        * Pega os 10 ultimos
        * */
        fibonacciGenerator.takeLast(10).subscribe(t -> {
            System.out.println(t);
        });

        /*
         * Pega o ultimo
         * */
        fibonacciGenerator.last().subscribe(t -> {
            System.out.println(t);
        });
    }
}
