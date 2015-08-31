import org.junit.Test;
import rx.Observable;
import rx.functions.Func1;
import rx.observables.GroupedObservable;

import static org.assertj.core.api.Assertions.assertThat;


public class lessonD_AdvancedStreams {

    public String mReceived = "";
    public String _____;

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
    public void _1_merging() {
        Observable<Object> you = Observable.just(1, 2, 3);
        Observable<String> me = Observable.just("A", "B", "C");

        you.mergeWith(me).subscribe(string -> mReceived += string + " ");

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
    public void _2_splittingUp() {
        Observable.range(1, 9)
                .groupBy(integer -> {
                    // ____
                    return _____;
                })
                .subscribe(group -> group.subscribe(integer -> {
                    String key = group.getKey();
                    if (key == "even") {
                        mEvenNums = mEvenNums + integer;
                    } else if (key == "odd") {
                        mOddNums = mOddNums + integer;
                    }
                }));

        assertThat(mEvenNums).isEqualTo("2468");
        assertThat(mOddNums).isEqualTo("13579");
    }


    /*
    Lets take what we know now and do some cool stuff. We've setup an observable and a function for you. Lets combine
    them together to average some numbers.

    Also see that we need to subscribe first to the "parent" observable but that the pipeline still cold until we
    subscribe to each subset observable. Don't forget to do that.
     */
    @Test
    public void _3_challenge_needToSubscribeImmediatelyWhenSplitting() {
        final double[] averages = {0, 0};
        Observable<Integer> numbers = Observable.just(22, 22, 99, 22, 101, 22);
        Func1<Integer, Integer> keySelector = integer -> integer % 2;
        Observable<GroupedObservable<Integer, Integer>> split = numbers.groupBy(keySelector);
        split.subscribe(
                group -> {
                    Observable<Double> convertToDouble = group.map(integer -> (double) integer);
                    Func1<Double, Double> insertIntoAveragesArray = aDouble -> averages[group.getKey()] = aDouble;
//                  MathObservable.averageDouble(________).map(____________).______();
                }
        );

        assertThat(averages[0]).isEqualTo(22.0);
        assertThat(averages[1]).isEqualTo(100.0);
    }
}