package br.com.reactivecore.demoapp.examples;

import reactor.core.publisher.Flux;

public class ExamploEtapasEvento {

    public static void main(String[] args) {
        var eventos = Flux.just(1,2,3);

        eventos.subscribe();
        eventos.subscribe(e ->  System.out.println(e));
        eventos.subscribe(t -> System.out.println("consuming " + t), e -> e.printStackTrace());
        eventos.subscribe(t -> System.out.println("consuming " + t), e -> e.printStackTrace(), () -> System.out.println("complete"));
    }
}
