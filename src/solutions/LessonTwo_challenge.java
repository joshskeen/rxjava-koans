package solutions;

import org.junit.Test;
import rx.Observable;
import rx.observables.MathObservable;

import static org.fest.assertions.Assertions.assertThat;

public class LessonTwo_challenge {

    private Integer mSum;

    @Test
    public void challenge_compositionMeansTheSumIsGreaterThanTheParts() {
        // --- one way:
        Observable<Integer> numbers = Observable.range(1, 10)
                .filter(integer -> integer > 8);

        MathObservable.sumInteger(numbers)
                .subscribe(integer -> mSum = integer);

        // ---- another way:
        MathObservable.sumInteger(Observable.range(1, 10)
                .filter(integer -> integer > 8))
                .subscribe(integer -> mSum = integer);

        assertThat(mSum).isEqualTo(19);
    }
}
