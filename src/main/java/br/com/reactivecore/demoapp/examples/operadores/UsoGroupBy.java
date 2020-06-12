package br.com.reactivecore.demoapp.examples.operadores;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UsoGroupBy {

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

        fibonacciGenerator.take(20)
                .groupBy(i -> {
                    List<Integer> divisors = Arrays.asList(2,3,5,7);
                    Optional<Integer> divisor = divisors.stream().filter(d -> i % d == 0).findFirst();
                    return divisor.map(x -> "Divisible by "+x).orElse("Others");
                }).concatMap(x -> {
                    System.out.println("\n"+x.key()); // imprime a string acima
                    return x;
        }).subscribe(x -> System.out.println(" " +x));
    }
}
