package org.reactive.reactorexample;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

public class ReactorTimingSnippets {

    private static final List<String> ANIMALS = List.of(
        "cat",
        "dog",
        "elephant",
        "fox",
        "bird",
        "rabbit",
        "ant",
        "giraffe",
        "lion"
    );

    @Test
    public void elapsed() {
        Flux<Tuple2<Long, String>> seq1 = Flux.fromIterable(ANIMALS)
                                              .elapsed();
        seq1.subscribe(System.out::println);
    }

    @Test
    public void timeout() {
        Flux<String> seq1 = Flux.fromIterable(ANIMALS)
                                .delayElements(Duration.ofSeconds(2))
                                .timeout(Duration.ofSeconds(1));
        seq1.toStream()
            .forEach(System.out::println);
    }

    @Test
    public void interval() {
        Flux<Long> seq1 = Flux.interval(Duration.ofSeconds(1))
                              .take(10);
        seq1.toStream()
            .forEach(System.out::println);
    }

    @Test
    public void monoDelay() throws InterruptedException {
        Mono<Long> seq1 = Mono.delay(Duration.ofSeconds(1));
        seq1.subscribe(System.out::println);
        Thread.sleep(1000);
    }

    @Test
    public void fluxDelayElements() {
        Flux<String> seq1 = Flux.fromIterable(ANIMALS)
                              .delayElements(Duration.ofSeconds(1));
        seq1.toStream()
            .forEach(System.out::println);
    }

    @Test
    public void fluxDelaySubscription() {
        Flux<String> seq1 = Flux.fromIterable(ANIMALS)
                                .delaySubscription(Duration.ofSeconds(1));
        seq1.toStream()
            .forEach(System.out::println);
    }
}
