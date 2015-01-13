package solutions;

import org.junit.Test;
import rx.Observable;
import rx.functions.Func1;
import rx.observables.GroupedObservable;
import rx.observables.MathObservable;

import static org.fest.assertions.Assertions.assertThat;

public class LessonFour_challenge {

    @Test
    public void challenge_needToSubscribeImmediatelyWhenSplitting() {
        final double[] averages = {0, 0};
        Observable<Integer> numbers = Observable.just(22, 22, 99, 22, 101, 22);
        Func1<Integer, Integer> keySelector = integer -> integer % 2;
        Observable<GroupedObservable<Integer, Integer>> split = numbers.groupBy(keySelector);
        split.subscribe(
                group -> {
                    Observable<Double> convertToDouble = group.map(integer -> (double) integer);
                    Func1<Double, Double> insertIntoAveragesArray = aDouble -> averages[group.getKey()] = aDouble;

                    MathObservable.averageDouble(convertToDouble).map(insertIntoAveragesArray).subscribe();
                }
        );

        assertThat(averages[0]).isEqualTo(22.0);
        assertThat(averages[1]).isEqualTo(100.0);
    }
}
