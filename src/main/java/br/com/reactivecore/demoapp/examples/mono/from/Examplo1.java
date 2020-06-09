package br.com.reactivecore.demoapp.examples.mono.from;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Examplo1 {

    public static void main(String[] args) {
        Mono.fromSupplier(() -> 1).subscribe(System.out::println);

        Mono.fromCallable(() -> new String[]{"color"}).subscribe(System.out::println);

        Mono.fromRunnable(() -> System.out.println("teste"))
                .subscribe(t -> System.out.println("received " + t),
                        null,
                        () -> System.out.println("Fim"));

        Mono.from(Flux.just("red", "blue", "black")).subscribe(t -> System.out.println("received " + t)); // ele so pega o primeiro evento, nesse caso
    }
}
