package org.reactive.reactorexample;

import java.util.List;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

public class ReactorPeekingSnippets {

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
    public void peekingDoOnNext() {
        Flux<String> seq1 = Flux.fromIterable(ANIMALS)
                                .doOnNext(a -> System.out.println("Next animal: "));
        seq1.subscribe(System.out::println);
    }

    @Test
    public void peekingDoOnComplete() {
        Flux<String> seq1 = Flux.fromIterable(ANIMALS)
                                .doOnComplete(() -> System.out.println("Completed..."));
        seq1.subscribe(System.out::println);
    }

    @Test
    public void peekingDoOnError() {
        Flux<String> seq1 = Flux.fromIterable(ANIMALS)
                                .map(a -> {
                                    if (!a.equals("fox")) return a;
                                    throw new RuntimeException("Got fox!");
                                })
                                .doOnError(e -> System.out.println("Error: " + e));
        seq1.subscribe(System.out::println);
    }

    @Test
    public void peekingDoFirst() {
        Flux<String> seq1 = Flux.fromIterable(ANIMALS)
                                .doFirst(() -> System.out.println("Start!"));
        seq1.subscribe(System.out::println);
    }

    @Test
    public void peekingDoOnSubscribe() {
        Flux<String> seq1 = Flux.fromIterable(ANIMALS)
                                .doOnSubscribe(sub -> {
                                    System.out.println("Cancelling subscription");
                                    sub.cancel();
                                });
        seq1.subscribe(System.out::println);
    }

    @Test
    public void peekingDoFinally() {
        Flux<String> seq1 = Flux.fromIterable(ANIMALS)
                                .doFinally( signalType -> System.out.println("Final signal: " + signalType));
        seq1.subscribe(System.out::println);
    }
}
