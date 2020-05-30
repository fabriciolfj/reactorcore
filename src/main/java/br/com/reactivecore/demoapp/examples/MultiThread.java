package br.com.reactivecore.demoapp.examples;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultiThread {

    public static void main(String[] args) {
        Flux.just(1,2,3,4,5,6,7,8,9,3,32,5,6,7,4,3,2,3,4)
                .log()
                .map(i -> i *2)
                .subscribeOn(Schedulers.fromExecutor(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())))
                .subscribe(System.out::println);

        try {
            TimeUnit.MILLISECONDS.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
