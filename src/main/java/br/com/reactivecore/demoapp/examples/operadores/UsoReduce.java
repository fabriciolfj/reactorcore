package br.com.reactivecore.demoapp.examples.operadores;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

public class UsoReduce {

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
        * pega os 10 primeiros, agrupa em um, devolvendo em um mono ao inscritor
        * */
        fibonacciGenerator.take(10).reduce((x,y) -> x + y).subscribe(t -> {
            System.out.println(t);
        });
    }
}
