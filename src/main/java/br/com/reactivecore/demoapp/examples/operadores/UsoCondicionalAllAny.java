package br.com.reactivecore.demoapp.examples.operadores;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

public class UsoCondicionalAllAny {

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
        * pega os 10 primeiros, verifica se todos atendem a condição, e retorna um Mono<Bollean>
        * */
        fibonacciGenerator.take(10).all(x -> x > 0).subscribe(t -> {
            System.out.println(t);
        });

        /*
         * pega os 10 primeiros, verifica se ao menos um atende a condição, e retorna um Mono<Bollean>
         * */
        fibonacciGenerator.take(10).any(x -> x > 0).subscribe(t -> {
            System.out.println(t);
        });
    }
}
