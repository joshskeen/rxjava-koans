import org.junit.Test;
import rx.Observable;
import rx.observables.StringObservable;

import java.util.Collections;

import static org.fest.assertions.Assertions.assertThat;

public class LessonOne_ObservableStreams {

    private Object mReceived;
    private Integer mReceivedInteger;
    private String mStatus;
    private Object _____;
    private int ____;

    @Test
    public void simpleSubscription() {
        Observable.just(42).subscribe(integer -> assertThat(integer).isEqualTo(____));
    }

    @Test
    public void simpleReturn() {
        mReceived = "";
        Observable.just("Foo").subscribe(s -> mReceived = s);
        assertThat(mReceived).isEqualTo(_____);
    }

    @Test
    public void theLastEvent() {
        mReceived = 0;
        Observable.just("Foo", "Bar").subscribe(string -> mReceived = string);

        assertThat(mReceived).isEqualTo(_____);
    }

    @Test
    public void everyThingCounts() {
        mReceivedInteger = 0;
        Observable.just(3, 4).subscribe(integer -> mReceivedInteger += integer);

        assertThat(mReceivedInteger).isEqualTo((Integer) _____);
    }

    @Test
    public void doingInTheMiddle() {
        mStatus = "";
        Observable<String> daysTillTest = Observable.range(1, 4).toList().map(integers -> {
            Collections.reverse(integers);
            return integers;
        }).flatMap(integers -> Observable.from(integers))
                .map(integer -> integer + "=" + (integer == 1 ? "Study Like Mad" : _____));
        StringObservable.join(daysTillTest, ",").subscribe(s -> mStatus = s);

        assertThat(mStatus).isEqualTo("4=Party,3=Party,2=Party,1=Study Like Mad");
    }

    @Test
    public void nothingListensUntilYouSubscribe() {
        mReceivedInteger = 0;
        Observable<Integer> numbers = Observable.range(1, 10).doOnNext(integer -> mReceivedInteger += integer);
        assertThat(mReceivedInteger).isEqualTo(0);
//        numbers.____();
        assertThat(mReceivedInteger).isEqualTo(1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 10);
    }

}
