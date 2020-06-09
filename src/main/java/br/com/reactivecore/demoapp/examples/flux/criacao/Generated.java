package br.com.reactivecore.demoapp.examples.flux.criacao;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import java.util.LinkedList;
import java.util.List;

public class Generated {

    /*
    * Aceita apenas um subscriber por publisher
    * Consome antes de gerar o próximo valor
    * gera eventos de forma sincrona
    * */
    public static void main(String[] args) {
        Flux<Long> fibonacciGenerator = Flux.generate(() ->
                Tuples.<Long, Long>of(0L, 1L), (state, sink) -> {
            sink.next(state.getT1());
            System.out.println("generated " + state.getT1());
            return Tuples.of(state.getT2(), state.getT1() + state.getT2());
        });

        List<Long> fibonacciSeries = new LinkedList<>();
        int size = 50;

        fibonacciGenerator.take(size).subscribe(t -> {
            System.out.println("consuming: " + t);
            fibonacciSeries.add(t);
        });

        //System.out.println(fibonacciSeries);
    }
}
