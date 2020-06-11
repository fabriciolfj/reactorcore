package br.com.reactivecore.demoapp.examples.operadores;

import br.com.reactivecore.demoapp.examples.Factorization;
import br.com.reactivecore.demoapp.examples.RomanNumber;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

public class UsoFlatmap {

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
        * Pegar o valor retornado pela fatoração, e gera um evento para cada um, por exempo: 1,2,3, Flux com esses eventos e assim para cada t
        * flatmap convert 1 para n, ou converte para um novo evento
        * */
        Factorization fator = new Factorization();
        fibonacciGenerator.skip(1).take(10).flatMap(t -> Flux.fromIterable(fator.findFactor(t.intValue())))
                .subscribe(System.out::println);
    }
}
