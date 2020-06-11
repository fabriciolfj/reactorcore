package br.com.reactivecore.demoapp.examples.operadores;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

public class UsoCollectMap {

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
        * pega os 10 primeiros, converte em um Map (hashmap) acumulando os dados,  e disponibiliza um mono ao inscritor
        * */
        fibonacciGenerator.take(10).collectMap(t -> t%2==0? "par" : "impar").subscribe(t -> {
            System.out.println(t);
        });

        /*
         * pega os 10 primeiros, converte em um Map (hashmap), e disponibiliza um mono ao inscritor
         * */
        fibonacciGenerator.take(10).collectMultimap(t -> t%2==0? "par" : "impar").subscribe(t -> {
            System.out.println(t);
        });
    }
}
