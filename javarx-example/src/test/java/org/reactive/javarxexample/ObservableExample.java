package org.reactive.javarxexample;

import io.reactivex.Observable;
import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

public class ObservableExample {


    List<String> words = Arrays.asList(
            "the",
            "quick",
            "brown",
            "fox",
            "jumped",
            "over",
            "the",
            "lazy",
            "dogs"
    );

    @Test
    public void simpleObservable() {
        Observable.just("Hello", "World")
                .subscribe(System.out::println);
    }

    @Test
    public void simpleObservableWithIteration() {
        Observable.fromIterable(words)
                .subscribe(System.out::println);
    }

    @Test
    public void simpleRangeCreation() {
        Observable.range(1, 5).subscribe(System.out::println);
    }

    @Test
    public void simpleZipOperation() {
        Observable.fromIterable(words)
                .zipWith(Observable.range(1, Integer.MAX_VALUE),
                        (string, count)->String.format("%2d. %s", count, string))
                .subscribe(System.out::println);
    }

    @Test
    public void simpleZipOperationWithFlatMap() {
        Observable.fromIterable(words)
                .flatMap(word -> Observable.fromArray(word.split("")))
                .zipWith(Observable.range(1, Integer.MAX_VALUE),
                        (string, count) -> String.format("%2d. %s", count, string))
                .subscribe(System.out::println);
    }

    @Test
    public void simpleZipWithDistinctLettersOperation() {
        Observable.fromIterable(words)
                .flatMap(word -> Observable.fromArray(word.split("")))
                .distinct()
                .zipWith(Observable.range(1, Integer.MAX_VALUE),
                        (string, count) -> String.format("%2d. %s", count, string))
                .subscribe(System.out::println);
    }

    @Test
    public void simpleZipWithDistinctLettersOperationSorted() {
        Observable.fromIterable(words)
                .flatMap(word -> Observable.fromArray(word.split("")))
                .distinct()
                .sorted()
                .zipWith(Observable.range(1, Integer.MAX_VALUE),
                        (string, count) -> String.format("%2d. %s", count, string))
                .subscribe(System.out::println);
    }

}
