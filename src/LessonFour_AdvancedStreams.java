import org.junit.Test;
import rx.Observable;
import rx.functions.Func1;
import rx.observables.GroupedObservable;
import rx.observables.MathObservable;

import java.util.HashMap;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

public class LessonFour_AdvancedStreams {

    public String mReceived = "";
    public Map<Integer, String> mStrings = new HashMap<>();
    public String _____;
    public Integer _______;

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


    @Test
    public void splittingUp() {
        mStrings.put(0, "");
        mStrings.put(1, "");
        Observable.range(1, 9).groupBy(integer -> _______)
                .subscribe(group -> group.subscribe(integer -> {
                    String s = mStrings.get(group.getKey()) + "" + integer;
                    mStrings.put(group.getKey(), s);
                }));
        assertThat(mStrings.get(0)).isEqualTo("2468");
        assertThat(mStrings.get(1)).isEqualTo("13579");
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
