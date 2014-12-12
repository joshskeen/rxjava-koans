import org.junit.Test;
import rx.Observable;
import rx.observables.MathObservable;
import rx.observables.StringObservable;

import static org.fest.assertions.Assertions.assertThat;

public class LessonTwo_ComposableObservations {

    public int mReceived;
    public String mValueA;
    public String mValueB;
    public Boolean mBooleanValue;
    private Object _____;
    private int ____;

    @Test
    public void composableAddition() {
        mReceived = 0;
        int ____ = 0;
        Observable<Integer> numbers = Observable.just(10, 100, ____);
        MathObservable.sumInteger(numbers).subscribe(integer -> mReceived = integer);
        assertThat(mReceived).isEqualTo(1110);
    }

    @Test
    public void composableBeforeAndAfter() {
        mValueA = "";
        mValueB = "";
        Observable.range(1, 6).doOnNext(integer -> mValueA += integer)
                .filter(integer -> integer % 2 == 0)
                .doOnNext(integer -> mValueB += integer)
                .subscribe();
        assertThat(mValueA).isEqualTo("123456");
        assertThat(mValueB).isEqualTo("246");
    }

    @Test
    public void weWroteThis() {
        mValueA = "";
        int ____ = 0;
        StringObservable.join(Observable.just("Josh", "Bill", "Joe", "Fredric")
                .filter(s -> s.length() <= ____), ",").subscribe(s -> mValueA = s);
        assertThat(mValueA).isEqualTo("Josh,Bill,Joe");
    }

    @Test
    public void convertingEvents() {
        mValueA = "";
        Observable.just("wE", "hOpe", "yOU", "aRe", "eNjOyInG", "thIS").map(s -> _____).subscribe(s -> mValueA += s + " ");
        assertThat(mValueA).isEqualTo("we hope you are enjoying this ");
    }

    @Test
    public void creatingAMoreRelevantEventStream() {
        mValueA = "";
        final int windowTopX = 50;
        Observable.just(100, 200, 150).map(integer -> integer - ____).subscribe(integer -> mValueA += integer + ", ");
        assertThat(mValueA).isEqualTo("50, 150, 100, ");
    }

    @Test
    public void checkingEverything() {
        mReceived = 0;
        Observable.just(2, 4, 6, 8).all(integer -> integer % 2 == 0).subscribe(aBoolean -> mBooleanValue = aBoolean);
        assertThat(_____).isEqualTo(true);
    }

    @Test
    public void compositionMeansTheSumIsGreaterThanTheParts() {
        int ____ = 0;
        MathObservable.sumInteger(Observable.range(1, 10).filter(integer -> integer > ____))
                .subscribe(integer -> assertThat(integer).isEqualTo(19));
    }


}
