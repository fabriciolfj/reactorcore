package br.com.reactivecore.demoapp.examples.processadores;

import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.FluxSink;

public class UsoEmitterProcessor {

    /**
     * os processadores são limitados, embora sejam bem simuladores aos publishers
     * tem controle backpressure, mas aceita mais de um assinante
     *
     */
    public static void main(String[] args) {
        EmitterProcessor<Long> data = EmitterProcessor.create(1); // cache de 1 evento
        //data.subscribe(t -> System.out.println(t));
        FluxSink<Long> sink = data.sink();
        sink.next(10L);
        sink.next(11L);
        sink.next(12L);
        data.subscribe(t -> System.out.println(t));
        sink.next(13L);
        sink.next(14L);
        sink.next(15L);


    }
}
