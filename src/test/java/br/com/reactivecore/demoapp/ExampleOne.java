package br.com.reactivecore.demoapp;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ExampleOne {

    @Test
    public void addElement() {
        List<Integer> elements = new ArrayList<>();
        Flux.just(1,2,3,4)
                .log()
                .subscribe(elements::add);

        assertThat(elements).containsExactly(1,2,3,4);
    }

    @Test
    public void multiplyElements() {
        List<Integer> elements = new ArrayList<>();
        Flux.just(1,2,3,4)
                .log()
                .map(x -> x * 2)
                .subscribe(elements::add);

        assertThat(elements).containsExactly(2,4,6,8);
    }

    @Test
    public void combiningStreams() {
        List<String> elements = new ArrayList<>();
        Flux.just(1,2,3,4)
                .log()
                .map(i -> i * 2)
                .zipWith(Flux.range(0, Integer.MAX_VALUE), (one, two) -> String.format("First Flux: %d, Second Flux: %d", one, two))
                .subscribe(elements::add);

        assertThat(elements).containsExactly(
                "First Flux: 2, Second Flux: 0",
                "First Flux: 4, Second Flux: 1",
                "First Flux: 6, Second Flux: 2",
                "First Flux: 8, Second Flux: 3");
    }
}
