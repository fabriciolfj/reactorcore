package br.com.reactivecore.demoapp.examples.operadores;

import br.com.reactivecore.demoapp.examples.RomanNumber;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

public class UsoMap {

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

        RomanNumber numberConversor = new RomanNumber();
        fibonacciGenerator.skip(1).take(10).map(t -> numberConversor.toRomanNumeral(t.intValue()))
                .subscribe(System.out::println);
    }
}
