package org.reactive.reactorexample;

import java.util.List;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactorCreationSnippets {

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
    public void simpleFluxJustCreation() {
        Flux<String> seq = Flux.just("Hello", "World");
        // seq.subscribe();
        seq.subscribe(System.out::println);
    }

    @Test
    public void simpleFluxFromIterableCreation() {
        Flux<String> seq = Flux.fromIterable(ANIMALS);
        seq.subscribe(System.out::println);
    }

    @Test
    public void simpleFluxRangeCreation() {
        Flux<Integer> seq = Flux.range(10, 5);
        seq.subscribe(System.out::println);
    }

    @Test
    public void simpleMonoEmptyCreation() {
        Mono<String> noData = Mono.empty();
        noData.subscribe(System.out::println);
    }

    @Test
    public void simpleMonoJustCreation() {
        Mono<String> data = Mono.just("Hello");
        data.subscribe(System.out::println);
    }

    @Test
    public void simpleMonoJustOrEmptyCreation() {
        Mono<String> noData = Mono.justOrEmpty(null);
        noData.subscribe(System.out::println);

        System.out.println();

        Mono<String> data = Mono.justOrEmpty("Hello");
        data.subscribe(System.out::println);
    }

    @Test
    public void subscriptionWithErrorHandling() {
        Flux<String> seq1 = Flux.fromIterable(ANIMALS)
                                 .map(a -> {
                                     if (!a.equals("fox")) return a;
                                     throw new RuntimeException("Got fox!");
                                 });
        seq1.subscribe(i -> System.out.println(i),
            error -> System.err.println("Error: " + error));
    }

    @Test
    public void subscriptionWithErrorAndCompletionHandling() {
        Flux<String> ints = Flux.fromIterable(ANIMALS);
        ints.subscribe(i -> System.out.println(i),
            error -> System.err.println("Error: " + error),
            () -> System.out.println("Done"));
    }

    @Test
    public void subscriptionWithErrorAndCompletionAndSubscriptionHandling() {
        Flux<String> ints = Flux.fromIterable(ANIMALS);
        ints.subscribe(i -> System.out.println(i),
            error -> System.err.println("Error: " + error),
            () -> System.out.println("Done"),
            sub -> sub.request(4));
    }
}
