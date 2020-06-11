package br.com.reactivecore.demoapp.examples.hotandcold;

import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;
import reactor.util.function.Tuples;

import java.util.concurrent.CountDownLatch;

public class Hot {
    /*
    * Simplificando, um CountDownLatch possui um campo de contador, que você pode diminuir conforme exigido.
    * Em seguida, podemos usá-lo para bloquear um segmento de chamada até que seja contado até zero.
    * Se estivéssemos fazendo algum processamento paralelo, poderíamos instanciar o CountDownLatch com o
    * mesmo valor para o contador que um número de threads que queremos trabalhar. Então, poderíamos simplesmente chamar countdown () após o término de cada thread,
    * garantindo que uma chamada dependente de waithave () seja bloqueada até que os threads de trabalho sejam concluídos.
    * */

    public static void main(String[] args) throws InterruptedException {
        final UnicastProcessor<Long> hotSource = UnicastProcessor.create();
        final Flux<Long> hotFlux = hotSource.publish().autoConnect();
        hotFlux.take(5).subscribe(t -> System.out.println("1. " + t));

        CountDownLatch latch = new CountDownLatch(2);
        new Thread(() -> {
            int c1 = 0, c2 = 1;
            while (c1 < 1000) {
                hotSource.onNext(Long.valueOf(c1));
                int sum = c1 + c2;
                c2 = sum;

                if (c1 == 144) {
                    hotFlux.subscribe(t -> System.out.println("2. " + t)); //adicionando um novo assinante
                }
            }
            hotSource.onComplete();
            latch.countDown();
        }).start();

        latch.await();


    }
}
