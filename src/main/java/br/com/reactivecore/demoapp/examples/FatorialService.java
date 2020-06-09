package br.com.reactivecore.demoapp.examples;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

public class FatorialService {

    public static void main(String[] args) {
        long number = 10;
        Flux<Double> factorialStream =
                Flux.generate(() -> Tuples.<Long, Double>of(0L, 1.0d),
                        (state, sink) -> {
                            Long factNumber = state.getT1();
                            Double factValue = state.getT2();

                            if(factNumber <= number) {
                                sink.next(factValue); //publica o valor
                            } else {
                                sink.complete(); //senao conclui
                            };

                            return Tuples.of(factNumber + 1, (factNumber + 1) * factValue);
                        });

        factorialStream.doOnNext(e -> System.out.println("valor gerado: " + e)).subscribe(System.out::println);
    }
}
