package br.com.reactivecore.demoapp.examples.operadores;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import java.util.HashSet;

public class UsoWindow {

    /***
     *  Um forma de estratégia de backpressure
     *  similar ao buffer, mas melhor, pois gerencia melhor a memória
     *  o resultado nao e uma lista e sim um conjunto de processadores
     *  todos os elementos não são publicados em uma unica janela (tem uma janela para cada elemento)
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

        /*fibonacciGenerator.window(10)
                .concatMap(x -> x)
                .subscribe(x -> System.out.println(x+ ""));*/

        /*fibonacciGenerator.windowWhile(x -> x < 500)
                .concatMap(x -> x)
                .subscribe(x -> System.out.println(x+ "")); // pegar todos os resultados em colocar em 1 janela*/

        fibonacciGenerator.windowUntil(x -> x < 500)
                .concatMap(x -> x)
                .subscribe(x -> System.out.println(x+ "")); // agrega todos os elementos não correspondentes a janela
    }
}
