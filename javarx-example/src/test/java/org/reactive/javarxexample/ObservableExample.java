package org.reactive.javarxexample;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.TestSubscriber;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void simpleFlowable() {
        Observable<Integer> integerObservable = Observable.just(1, 2, 3);
        Flowable<Integer> integerFlowable = integerObservable
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Test
    public void simpleFlowableBackpressureStrategyBUFFER() {
        List testList = IntStream.range(0, 100000)
                .boxed()
                .collect(Collectors.toList());

        Observable observable = Observable.fromIterable(testList);
        TestSubscriber<Integer> testSubscriber = observable
                .toFlowable(BackpressureStrategy.BUFFER)
                .observeOn(Schedulers.computation()).test();

        testSubscriber.awaitTerminalEvent();

        List<Integer> receivedInts = testSubscriber.getEvents()
                .get(0)
                .stream()
                .mapToInt(object -> (int) object)
                .boxed()
                .collect(Collectors.toList());

        assertEquals(testList, receivedInts);
    }

    @Test
    public void simpleFlowableBackpressureStrategyDROP() {
        List testList = IntStream.range(0, 100000)
                .boxed()
                .collect(Collectors.toList());
        Observable observable = Observable.fromIterable(testList);
        TestSubscriber<Integer> testSubscriber = observable
                .toFlowable(BackpressureStrategy.DROP)
                .observeOn(Schedulers.computation())
                .test();
        testSubscriber.awaitTerminalEvent();
        List<Integer> receivedInts = testSubscriber.getEvents()
                .get(0)
                .stream()
                .mapToInt(object -> (int) object)
                .boxed()
                .collect(Collectors.toList());

        assertTrue(receivedInts.size() < testList.size());
        assertTrue(!receivedInts.contains(100000));
    }

}
