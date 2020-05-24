package org.reactive.reactorexample;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

public class ReactorTransformationSnippets {

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
    public void transformOneToOne() {
        Flux<String> seq1 = Flux.fromIterable(ANIMALS)
                                .map(a -> a.toUpperCase());
        seq1.subscribe(System.out::println);

        System.out.println();

        Flux<String> seq2 = Flux.fromIterable(ANIMALS)
                                .index((id, el) -> id + ": " + el);
        seq2.subscribe(System.out::println);
    }

    @Test
    public void transformOneToManyFlux() {
        Flux<String> seq1 = Flux.fromIterable(ANIMALS)
                                .flatMap(a -> Flux.fromArray(a.split("")));
        seq1.subscribe(System.out::println);
    }

    @Test
    public void transformOneToManyMono() {
        Flux<String> seq1 = Mono.just("Hello")
                                .flatMapMany(word -> Flux.fromArray(word.split("")));
        seq1.subscribe(System.out::println);
    }

    //============= add pre-set elements to an existing sequence =============

    @Test
    public void transformStartWith() {
        Flux<String> seq1 = Flux.just("Greg", "John")
                                .startWith("Mike", "Josh");
        seq1.subscribe(System.out::println);
    }

    @Test
    public void transformConcatWithValues() {
        Flux<String> seq1 = Flux.just("Greg", "John")
                                .concatWithValues("Mike", "Josh");
        seq1.subscribe(System.out::println);
    }

    //============= aggregate =============

    @Test
    public void transformCollect() {
        Mono<Set<String>> seq1 = Flux.just("Greg", "John", "Greg")
                                     .collect(Collectors.toSet());
        seq1.subscribe(System.out::println);
    }

    @Test
    public void transformCollectMap() {
        Mono<Map<Integer, String>> seq1 = Flux.fromIterable(ANIMALS)
                                              .collectMap(a -> a.hashCode());
        seq1.subscribe(System.out::println);
    }

    @Test
    public void transformCount() {
        Mono<Long> seq1 = Flux.range(10, 5)
                              .count();
        seq1.subscribe(System.out::println);
    }

    @Test
    public void transformReduce() {
        Mono<Integer> seq1 = Flux.range(10, 5)
                                 .reduce(Integer::sum);
        seq1.subscribe(System.out::println);
    }

    @Test
    public void transformAll() {
        Mono<Boolean> seq1 = Flux.fromIterable(ANIMALS)
                                 .all(a -> a.startsWith("a"));
        seq1.subscribe(System.out::println);
    }

    @Test
    public void transformAny() {
        Mono<Boolean> seq1 = Flux.fromIterable(ANIMALS)
                                 .any(a -> a.startsWith("a"));
        seq1.subscribe(System.out::println);
    }

    //============= combine publishers =============

    @Test
    public void transformConcatWith() {
        Flux<String> seq1 = Flux.just("Greg", "John")
                                .delayElements(Duration.ofSeconds(1))
                                .concatWith(Flux.just("Mike", "Josh"));
        seq1.toStream()
            .forEach(System.out::println);
    }

    @Test
    public void transformMergeWith() {
        Flux<String> seq1 = Flux.just("Greg", "John")
                                .delayElements(Duration.ofSeconds(1))
                                .mergeWith(Flux.just("Mike", "Josh"));
        seq1.toStream()
            .forEach(System.out::println);
    }

    @Test
    public void transformZipWithFlux() {
        Flux<Tuple2<String, String>> seq1 = Flux.just("Greg", "John")
                                                .zipWith(Flux.just("Mary", "Rose"));
        seq1.toStream()
            .forEach(System.out::println);
    }

    @Test
    public void transformZipWithMono() {
        Mono<Tuple2<String, String>> seq1 = Mono.just("Greg")
                                                .zipWith(Mono.just("Mary"));
        seq1.subscribe(System.out::println);
    }

    @Test
    public void transformCombineLatest() {
        Flux<String> seq1 = Flux.combineLatest(
            Flux.just("Greg"),
            Flux.just("Mary", "Rose"),
            (m, w) -> m + " " + w);
        seq1.subscribe(System.out::println);
    }

    //============= repeat an existing sequence =============

    @Test
    public void transformRepeat() {
        Flux<Integer> seq1 = Flux.range(1, 3)
                                 .repeat(1);
        seq1.subscribe(System.out::println);
    }

    //============= ignore sequence values =============

    @Test
    public void transformIgnoreElements() {
        Mono<String> seq1 = Flux.fromIterable(ANIMALS)
                                .ignoreElements()
                                .then(Mono.just("first"));
        seq1.subscribe(System.out::println);

        System.out.println();

        Flux<String> seq2 = Flux.fromIterable(ANIMALS)
                                .ignoreElements()
                                .thenMany(Flux.just("first","second"));
        seq2.subscribe(System.out::println);
    }
}
