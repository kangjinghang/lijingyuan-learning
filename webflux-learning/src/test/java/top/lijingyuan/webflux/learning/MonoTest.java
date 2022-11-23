package top.lijingyuan.webflux.learning;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

/**
 * Test of {@link Mono}
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2022-10-30
 * @since 1.0.0
 */
public class MonoTest {

    @Test
    public void testMono() {
//        final Mono<String> mono = Mono.just("hello");
        final Mono<?> mono = Mono.just("hello")
                .then(Mono.error(new RuntimeException("exception occurd")))
                .log();
        mono.subscribe(System.out::println, e -> System.out.println(e.getMessage()));
    }

    @Test
    public void testFlux() {
        final Flux<String> fluxString = Flux.just("Spring", "Spring Boot", "Hibernate", "microservcie")
                .concatWithValues("AWS")
                .concatWith(Flux.error(new RuntimeException("exception occurd")))
                .concatWithValues("cloud")
                .log();
        fluxString.subscribe(System.out::println, e -> System.out.println(e.getMessage()));
    }

    @Test
    public void testFlux1() {
        final Flux<String> fluxString = Flux.just("Spring", "Spring Boot");
        StepVerifier.create(fluxString)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectComplete()
                .verify();
    }


    @Test
    public void testFluxWithMockTimer() {
        StepVerifier.withVirtualTime(() -> Flux
                        .just("Spring", "Spring Boot")
                        .delayElements(Duration.ofSeconds(3L)))
                .expectSubscription()
                .expectNoEvent(Duration.ofSeconds(3L))
                .expectNext("Spring")
                .expectNoEvent(Duration.ofSeconds(3L))
                .expectNext("Spring Boot")
                .expectComplete()
                .verify();
    }


}
