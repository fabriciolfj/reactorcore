package br.com.reactivecore.demoapp;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

public class UsoPublishConnectedFlux {

    /*
     * ao contrario do replay, recebe eventos do fluxo de origem.
     * ou seja, ele acompanha as demandas levantadas por seus assinantes.
     * Se algum assinante não aumentar a demanda, pausa a geração de eventos até que uma nova demanda seja levantada por todos os seus assinantes.
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
        }).publish().autoConnect(2); // vai gerenciar os inscritos, e somente vai emitir eventos se no mínimo 2 estejam inscritos

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
                System.out.println("[Fib] 1nd: " + value);
            }
        });

        broadcastGenerator.subscribe(x -> System.out.println(" 2and: " + x)); // ja não emitie eventos, pois o fluxo anterior não terminou, ele estava esperando
        //o próximo pedido de evento do assinante 1
        Thread.sleep(500);

    }
}
