package br.com.reactivecore.demoapp.examples.filter;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

public class Exemplo1 {

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
        * sincrono
        * */
        fibonacciGenerator.filter(a -> a%2 == 0).subscribe(t -> {
            System.out.println(t);
        });

        /*
        * assincrono
        * */
        fibonacciGenerator.filterWhen(a -> Mono.just(a%2 == 0)).subscribe(t -> {
            System.out.println(t);
        });
    }
}
