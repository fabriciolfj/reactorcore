package br.com.reactivecore.demoapp;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

public class UsoBroadCast {

    /*
     * Gera eventos para n assinantes incritos.
     * */
    @Test
    public void test() {
        Flux<Long> fibonnaciGenerator = Flux.generate(() ->
                Tuples.of(0L, 1L), (state, sink) -> {
            if (state.getT1() < 0) {
                sink.complete();
            } else {
                sink.next(state.getT1());
            }
            System.out.println("Gerando next of " + state.getT2());
            System.out.println("Name thread dentro " + Thread.currentThread().getName());
            return Tuples.of(state.getT2(), state.getT1() + state.getT2());
        });

        Flux<Long> broadcastGenerator = fibonnaciGenerator.doFinally(x -> {
            System.out.println("Closing ");
        }).replay().autoConnect(2); // vai gerenciar os inscritos, e somente vai emitir eventos se no mínimo 2 estejam inscritos

        fibonnaciGenerator.subscribe(x -> System.out.println("[Fib] 1st: " + x));
        fibonnaciGenerator.subscribe(x -> System.out.println("[Fib] 2nd: " + x));

        broadcastGenerator.subscribe(x -> System.out.println(" 1st: " + x)); // vai emitir todos os eventos, ate que o onfinally chegue, e não acompanha se algum inscrito cancelou sua assinatura
        broadcastGenerator.subscribe(x -> System.out.println(" 2nd: " + x));

    }
}
