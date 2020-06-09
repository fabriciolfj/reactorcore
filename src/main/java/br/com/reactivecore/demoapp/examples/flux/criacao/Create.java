package br.com.reactivecore.demoapp.examples.flux.criacao;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Create {

    /*
    * gera qualquer numero de eventos
    * não respeita o cancelamento de eventos
    * gera eventos de forma assincrona
    * os eventos sao descartados, assim que a assinatura for cancelada.
    * */
    public static void main(String[] args) {
        Flux<Long> fibonnacciGenerator = Flux.create(e -> {
            Long current = 1L, prev = 0L;
            AtomicBoolean stop = new AtomicBoolean(false);
            e.onDispose(() -> { //é invocado quando a assinatura for encerrada
                stop.set(true);
                System.out.println("==== stop Received ====");
            });

            while(current > 0 ){
                e.next(current);
                System.out.println("generated " + current);
                long next = current + prev;
                prev = current;
                current = next;
            }
            e.complete();
        });

        List<Long> fibonnacciSeries = new LinkedList<>();
        fibonnacciGenerator.take(50).subscribe(t -> {
            System.out.println("consuming " + t);
            fibonnacciSeries.add(t);
        });

        System.out.println(fibonnacciSeries);
    }
}
