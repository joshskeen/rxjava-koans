import org.junit.Test;
import rx.Observable;

import static org.fest.assertions.Assertions.assertThat;

public class LessonOne_ObservableStreams {

    private Object mReceived;
    private Integer mSum;
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

    /*
    So far we've created observables and immediately "subscribed" to them. Its only when we subscribe to an
    observable that it is fully wired up. This observable is now considered "hot". Until then it is "cold"
    and doesn't really do anything, it won't emit any items.

    So if we are going to build an observable and not subscribe to it until later on, how can we include the all
    of the functionality as before? Do we have to put all the work inside subscribe() ? No we don't!

    If we peek at the Observer interface we see it has three methods:

        public interface Observer<T> {
            void onCompleted();

            void onError(Throwable var1);

            void onNext(T var1);
        }

    When we subscribe to an Observable, the code we put inside subscribe() is getting handed off to the Observer's onNext() method.
    However, we can manually pass code right to onNext() ourselves with Observable.doOnNext()

    Lets setup an Observable with all the functionality we need to sum a range of Integers. Then lets subscribe to it later on.
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
