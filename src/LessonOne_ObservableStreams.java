import org.junit.Test;
import rx.Observable;
import rx.observables.StringObservable;

import java.util.Collections;

import static org.fest.assertions.Assertions.assertThat;

public class LessonOne_ObservableStreams {

    private Object mReceived;
    private Integer mSum;
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

    /*
    Observables are ultimately about handling "streams" of items (i.e. more than one item).
    Here we have two items that we are inserting into the pipeline.
    Lets update our member variable for each item emitted. Which item do you think will be last?
     */
    @Test
    public void theLastEvent() {
        mReceived = "";
        Observable.just("Foo", "Bar").subscribe(string -> mReceived = string);

        assertThat(mReceived).isEqualTo(_____);
    }

    /*
    Each item that enters the pipeline is handled by our observable pipeline, not just the last one.
    Lets keep track of each item by adding them all up.
    */
    @Test
    public void everyThingCounts() {
        mSum = 0;
        Observable.just(3, 4).subscribe(integer -> mSum += integer);
        assertThat(mSum).isEqualTo((Integer) ____);
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

    /*
    So far we've created observables and immediately "subscribed" to them. Its only when we subscribe to an
    observable that it is fully wired up. This observable is now considered "hot". Until then it is "cold"
    and doesn't really do anything.

    We can create an observable and hold onto it and subscribe later. Lets try that here.
     */
    @Test
    public void nothingListensUntilYouSubscribe() {
        mSum = 0;
        Observable<Integer> numbers = Observable.range(1, 10).doOnNext(integer -> mSum += integer);
        assertThat(mSum).isEqualTo(0);
//        numbers.____();
        assertThat(mSum).isEqualTo(1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 10);
    }

}
