package br.com.reactivecore.demoapp.api.routes;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

@Component
public class FibonacciRoutes {

    @Bean
    RouterFunction<ServerResponse> fibonacciEndPoint() {
        Flux<Long> fibonacciGenerator = Flux.generate(() ->
                Tuples.of(0L, 1L), (state, sink) -> {


            if (state.getT1() < 0) {
                sink.complete();
            } else  {
                sink.next(state.getT1());
            }

            return Tuples.of(state.getT2(), state.getT1() + state.getT2());
        });

        RouterFunction<ServerResponse> fibonacciRoute = RouterFunctions
                .route(RequestPredicates.path("/fibonacci"), request -> ServerResponse.ok().body(
                        fibonacciGenerator, Long.class
                ));
        return fibonacciRoute;
    }
}
