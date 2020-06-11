package br.com.reactivecore.demoapp.examples.operadores;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

public class UsoCollectList {

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
        * pega os 10 primeiros, converte em uma list dentro de um mono, e disponibiliza ao inscritor
        * */
        fibonacciGenerator.take(10).collectList().subscribe(t -> {
            System.out.println(t);
        });

        /*
         * pega os 10 primeiros, converte em uma list, conforme a ordenação passada, dentro de um mono, e disponibiliza ao inscritor
         * */
        fibonacciGenerator.take(10).collectSortedList((x,y) -> -1*Long.compare(x,y)).subscribe(t -> {
            System.out.println(t);
        });
    }
}
