package br.com.reactivecore.demoapp.examples.onbackpressure;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Examplo1 {

    public static void main(String[] args) throws InterruptedException {
        Flux<Integer> numberGenerator = Flux.create(x -> {
            System.out.println("Request events: "+x.requestedFromDownstream());
            int number = 1;
            while(number < 100) {
                x.next(number);
                number++;
            }

            x.complete();
        });

        CountDownLatch latch = new CountDownLatch(1);
        numberGenerator.onBackpressureBuffer(2, x -> System.out.println("Dropped :"+x), BufferOverflowStrategy.DROP_LATEST) // coloca a capacidade no buffer e descarta os mais recentes
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected Subscription upstream() {
                        return super.upstream();
                    }
                });

        latch.await(1L, TimeUnit.SECONDS);
    }
}
