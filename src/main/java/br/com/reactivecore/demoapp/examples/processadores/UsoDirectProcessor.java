package br.com.reactivecore.demoapp.examples.processadores;

import reactor.core.publisher.DirectProcessor;

public class UsoDirectProcessor {

    /**
     * os processadores são limitados, embora sejam bem simuladores aos publishers, e entrega os eventos depois que o assinante assina,
     * ja o flux entrega todos os itens a todos assinantes, independentemente do tempo de assinatura
     * não tem controle backpressure
     *
     */
    public static void main(String[] args) {
        DirectProcessor<Long> data = DirectProcessor.create();
        data.take(2).subscribe(t -> System.out.println(t),
                e -> e.printStackTrace(),
                () -> System.out.println("Complete")
                //,s -> s.request(1L) vai dar erro, pois estou emitindo 1 evento a mais que o processador aguenta
        );

        data.onNext(10L);
        data.onNext(11L);
        data.onNext(12L);


    }
}
