import org.junit.Test;
import rx.Observable;
import rx.functions.Func1;
import rx.observables.GroupedObservable;
import rx.observables.MathObservable;

import static org.fest.assertions.Assertions.assertThat;

public class LessonFour_AdvancedStreams {

    public String mReceived = "";
    public String _____;
    public Integer _______;

    private String mEvenNums = "";
    private String mOddNums = "";

    /*
    So far everything has been pretty linear. Our pipelines all took the form:
    "do this, then do this, then do this, then end". In reality we can combine pipelines. We can take two streams
    and turn them into a single stream.

    Now its worth nothing this is different from what we did when we nested Observables. In that case we always had one stream.
    Lets take a stream of integers and a stream of strings and join them.
    */
    @Test
    public void merging() {
        Observable<Object> you = Observable.just(1, 2, 3);
        Observable<String> me = Observable.just("A", "B", "C");
        you.mergeWith(me)
                .subscribe(string -> mReceived += string + " ");

        assertThat(mReceived).isEqualTo(_____);
    }

    /*
    We can also split up a single stream into two streams. We are going to to use the groupBy() action.
    This action can be a little tricky because it emits an observable of observables. So we need to subscribe to the
    "parent" observable and each emitted observable.

    We encourage you to read more from the wiki: http://reactivex.io/documentation/operators/groupby.html

    Lets split up a single stream of integers into two streams: even and odd numbers.
    */
    @Test
    public void splittingUp() {
        Observable.range(1, 9)
                .groupBy(integer -> {
                    // ____
                    return _____;
                })
                .subscribe(subset -> subset.subscribe(integer -> {
                    String key = subset.getKey();
                    if (key == "even") {
                        mEvenNums = mEvenNums + integer;
                    } else if (key == "odd") {
                        mOddNums = mOddNums + integer;
                    }
                }));

        assertThat(mEvenNums).isEqualTo("2468");
        assertThat(mOddNums).isEqualTo("13579");
    }

    @Test
    public void needToSubscribeImmediatelyWhenSplitting() {
        final double[] avgs = {0.0, 0, 0};
        Observable<Integer> numbers = Observable.just(22, 22, 99, 22, 101, 22);
        Func1<Integer, Integer> keySelector = integer -> integer % 2;
        Observable<GroupedObservable<Integer, Integer>> split = numbers.groupBy(keySelector);
        split.subscribe(group -> MathObservable.averageDouble(
                group.map(integer -> new Double(integer))
        )//.____(aDouble -> avgs[group.getKey()] = aDouble)
        );
        assertThat(avgs[0]).isEqualTo(22.0);
        assertThat(avgs[1]).isEqualTo(100.0);
    }

}
