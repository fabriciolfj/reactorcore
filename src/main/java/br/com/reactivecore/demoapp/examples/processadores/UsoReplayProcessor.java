package br.com.reactivecore.demoapp.examples.processadores;

import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.ReplayProcessor;

public class UsoReplayProcessor {

    /**
     * os processadores são limitados, embora sejam bem simuladores aos publishers
     * indicado para os seguintes cenários: eventos limitados, eventos limitados por um período de tempo especificado ou uma contagem.
     *
     */
    public static void main(String[] args) {
        ReplayProcessor<Long> data = ReplayProcessor.create(3); //cache de 3 eventos
        data.subscribe(t -> System.out.println(t));
        FluxSink<Long> sink = data.sink();
        sink.next(10L);
        sink.next(11L);
        sink.next(12L);
        sink.next(13L);
        sink.next(14L);

        data.subscribe(t -> System.out.println(t)); // ele vai armazenar os 3 ultimos, e o segundo assinante vai imprimir o que estiver no cache
    }

}
