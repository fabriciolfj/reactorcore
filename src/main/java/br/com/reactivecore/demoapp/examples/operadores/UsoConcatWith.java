package br.com.reactivecore.demoapp.examples.operadores;

import br.com.reactivecore.demoapp.examples.Factorization;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

public class UsoConcatWith {

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
        * Pega os 10 valores, e concatena no final o flux passado.
        * */
        fibonacciGenerator.skip(1).take(10)
                .concatWith(Flux.just(new Long[]{-1L,-2L,-3L}))
                .subscribe(System.out::println);
    }
}
