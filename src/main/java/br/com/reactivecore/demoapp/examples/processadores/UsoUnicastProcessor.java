package br.com.reactivecore.demoapp.examples.processadores;

import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.UnicastProcessor;

public class UsoUnicastProcessor {

    /**
     * os processadores são limitados, embora sejam bem simuladores aos publishers
     * tem controle backpressure, mas aceita apenas um assinante
     *
     */
    public static void main(String[] args) {
        UnicastProcessor<Long> data = UnicastProcessor.create();
        data.take(2).subscribe(t -> System.out.println(t),
                e -> e.printStackTrace(),
                () -> System.out.println("Complete")
                ,s -> s.request(1L)
        );

        /*
        * sink e mais seguro do que o onNext, para publicar eventos
        * */
        data.sink().next(10L);
        data.sink().next(11L);
        data.sink().next(12L);


    }
}
