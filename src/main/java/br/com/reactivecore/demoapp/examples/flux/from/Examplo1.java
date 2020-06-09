package br.com.reactivecore.demoapp.examples.flux.from;

import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Examplo1 {

    public static void main(String[] args) {
        Flux.fromArray(new Integer[]{1,2,3,4,5,6}).subscribe(System.out::println);
        Flux.fromIterable(Arrays.asList("Red", "blue", "yellow")).subscribe(System.out::println);
        Flux.fromStream(IntStream.range(1,100).boxed()).subscribe(System.out::println);
    }
}
