package br.com.reactivecore.demoapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@SpringBootTest
class DemoAppApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void test() {
		var client = WebClient.create("http://127.0.0.1:8080");
		Flux<Long> dados = client
				.get()
				.uri("/fibonacci")
				.retrieve()
				.bodyToFlux(Long.class)
				.limitRequest(10L);

		dados.subscribe(System.out::println);
	}

}
