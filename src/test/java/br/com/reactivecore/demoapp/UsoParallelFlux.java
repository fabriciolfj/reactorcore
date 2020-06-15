package br.com.reactivecore.demoapp;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuples;

public class UsoParallelFlux {

    /*
    * capaz de dividir um fluxo existente, em vários fluxos de forma redonda.
    * ParallelFlux é criado a partir de um fluxo existente, utilizando o operador paralelo.
    * Por padrão isso divide o fluxo no número total de núcleos de CPU disponíveis.
    * ParallelFluxo so divide o fluxo, e não altera o modelo de execução.
    * ele pode ser executado utilizando o runON, semelhante ao publishOn, ele pega o scheduler e executa um downstream (dados do servidor para o client) nele.
    * ele nã tem o doFinally
    * ele pode ser convertido utilizando de volta o operador sequencial.
    * */

    @Test
    public void testReactor() throws Exception {
        Flux<Long> fibonnaciGenerator = Flux.generate(() ->
                Tuples.of(0L, 1L), (state, sink) -> {
            if (state.getT1() < 0) {
                sink.complete();
            } else {
                sink.next(state.getT1());
            }
            System.out.println("Gerando next of " + state.getT2());
            System.out.println("Name thread dentro "  + Thread.currentThread().getName());
            return Tuples.of(state.getT2(), state.getT1() + state.getT2());
        });

        fibonnaciGenerator
                .parallel()
                .runOn(Schedulers.parallel())
                .filter(x -> {
                    System.out.println("Executando o filter");
                    return x < 100;
                })
                .doOnNext(x -> System.out.println("Next value is " + x))
                .sequential() // converte para um fluxo sequencial
                .doFinally(x -> System.out.println("Closing"))
                .subscribeOn(Schedulers.single()) // será executado em um único seguimento.
                .subscribe(x -> System.out.println("Valor recebido: " + x));

        System.out.println("Name thread fora "  + Thread.currentThread().getName());
        Thread.sleep(500);
    }
}
