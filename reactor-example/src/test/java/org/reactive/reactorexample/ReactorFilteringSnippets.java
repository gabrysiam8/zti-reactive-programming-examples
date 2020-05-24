package org.reactive.reactorexample;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactorFilteringSnippets {

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
    public void filter() {
        Flux<String> seq1 = Flux.fromIterable(ANIMALS)
                                .filter(a -> a.endsWith("t"));
        seq1.subscribe(System.out::println);
    }

    @Test
    public void filterWhen() {
        Flux<String> seq1 = Flux.fromIterable(ANIMALS)
                                .filterWhen(a -> Mono.just(a.endsWith("t")));
        seq1.subscribe(System.out::println);
    }

    @Test
    public void distinct() {
        Flux<String> seq1 = Flux.just("one", "two", "one", "three")
                                .distinct();
        seq1.subscribe(System.out::println);
    }

    @Test
    public void takeN() {
        Flux<String> seq1 = Flux.fromIterable(ANIMALS)
                                .take(3);
        seq1.subscribe(System.out::println);
    }

    @Test
    public void takeDuration() {
        Flux<String> seq1 = Flux.fromIterable(ANIMALS)
                                .delayElements(Duration.ofMillis(50))
                                .take(Duration.ofMillis(200));
        seq1.toStream()
            .forEach(System.out::println);
    }

    @Test
    public void takeNextElement() {
        Mono<String> seq1 = Flux.fromIterable(ANIMALS)
                                .next();
        seq1.subscribe(System.out::println);
    }

    @Test
    public void takeNLastElements() {
        Flux<String> seq1 = Flux.fromIterable(ANIMALS)
                                .takeLast(2);
        seq1.subscribe(System.out::println);
    }

    @Test
    public void takeElementAt() {
        Mono<String> seq1 = Flux.fromIterable(ANIMALS)
                                .elementAt(4);
        seq1.subscribe(System.out::println);
    }

    @Test
    public void skipNElements() {
        Flux<String> seq1 = Flux.fromIterable(ANIMALS)
                                .skip(4);
        seq1.subscribe(System.out::println);
    }
}
