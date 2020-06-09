package br.com.reactivecore.demoapp.examples.mono.just;

import reactor.core.publisher.Mono;

import java.util.Optional;

public class Examplo1 {

    public static void main(String[] args) {
        Mono.just(1).subscribe(System.out::println);
        Mono.justOrEmpty(1).subscribe(System.out::println);
        Mono.justOrEmpty(Optional.empty()).subscribe(System.out::println);
    }
}
