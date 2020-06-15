package br.com.reactivecore.demoapp;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

public class UsoBroadCastRecount {

    /*
     * Gera eventos para n assinantes incritos.
     * */
    @Test
    public void test() throws InterruptedException {
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

        fibonnaciGenerator.subscribe(new BaseSubscriber<Long>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(1);
            }

            @Override
            protected void hookOnNext(Long value) {
                System.out.println("[Fib] 1nd: " + value);
                cancel();
            }
        });

        fibonnaciGenerator.subscribe(new BaseSubscriber<Long>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(1);
            }

            @Override
            protected void hookOnNext(Long value) {
                System.out.println("[Fib] 2nd: " + value);
                cancel();
            }
        });

        broadcastGenerator.subscribe(x -> System.out.println(" 1st: " + x)); // ja não emitie eventos, pois os incritos dentro do fibonnacigenerator, foi cancelado
        Thread.sleep(500);

    }
}
