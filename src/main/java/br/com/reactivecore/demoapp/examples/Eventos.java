package br.com.reactivecore.demoapp.examples;

import reactor.core.publisher.Flux;

public class Eventos {

    public static void main(String[] args) {
        var eventos = Flux.just(1,2,3,4);

        eventos.doOnNext(e -> System.out.println("next " + e)).subscribe(t -> System.out.println("Consumindo: " + t));
        eventos.doOnRequest(e -> System.out.println("request : " + e)).subscribe(t -> System.out.println("Consumindo: " + t));
        eventos.doOnError(e -> System.out.println("Error " + e))
                .subscribe(t ->
                    System.out.println("Divisao : " + t/0), e -> e.printStackTrace());

        eventos.doOnComplete(() -> System.out.println("complete ")).subscribe(t -> System.out.println("Consumindo: " + t));
    }
}
