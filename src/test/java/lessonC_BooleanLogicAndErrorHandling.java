import org.junit.Before;
import org.junit.Test;
import rx.Observable;
import rx.functions.Func1;
import rx.observables.MathObservable;
import rx.observers.TestSubscriber;
import util.LessonResources;
import util.LessonResources.ComcastNetworkAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static util.LessonResources.Elevator;
import static util.LessonResources.ElevatorPassenger;


public class lessonC_BooleanLogicAndErrorHandling {

    private static final Observable<?> ________ = null;
    public int mSum;
    public String mStringA;
    public String mStringB;
    public Boolean mBooleanValue;

    private String _____;
    private int ____;
    private Object ______ = "";
    private TestSubscriber<Object> mSubscriber;
    private List<Observable<Observable>> mField;
    Func1<ElevatorPassenger, Boolean> _______ = elevatorPassenger -> false;

    @Before
    public void setup() {
        mSubscriber = new TestSubscriber<>();
    }

    /**
     * In this section we will learn about boolean logic we can apply to our pipelines of data.
     * Our first stop on the tour is takeWhile(), similar in concept to the while loop you may already be familiar with.
     * http://reactivex.io/documentation/operators/takewhile.html
     * <p>
     * In this experiment, we will load elevators with passengers eager to reach their destinations. One thing:
     * Our elevator has a maximum capacity. If we overload it, our passengers may be injured or even die!
     * We will use takeWhile to ensure no elevator is overloaded.
     */
    @Test
    public void takeWhileEvaluatesAnExpressionAndEmitsEventsUntilItReturnsFalse() {

        LessonResources.Elevator elevator = new LessonResources.Elevator();

        Observable<ElevatorPassenger> elevatorQueueOne = Observable.from(Arrays.asList(
                new ElevatorPassenger("Max", 168),
                new ElevatorPassenger("Mike", 234),
                new ElevatorPassenger("Ronald", 192),
                new ElevatorPassenger("William", 142),
                new ElevatorPassenger("Jacqueline", 114)));

        Observable<ElevatorPassenger> elevatorQueueTwo = Observable.from(Arrays.asList(
                new ElevatorPassenger("Randy", 320),
                new ElevatorPassenger("Jerome", 125),
                new ElevatorPassenger("Sally-Joe", 349),
                new ElevatorPassenger("Little Eli", 54)));

        /**
         * the takeWhile operator evaluates an expression each time a new item is emitted in the stream.
         * As long as it returns true, the Observable stream of data takeWhile operates on continues to emit more data/events.
         *
         * Riddle: Lets define our elevator rule: the total weight of all passengers aboard an elevator may not be larger than 500 pounds.
         * How!?!
         */

        Func1<ElevatorPassenger, Boolean> elevatorRule = passenger -> ____ + ____ < ____;
        /**
         * Now all we need to do is to plug in the rule in takeWhile()
         */
        elevatorQueueOne.takeWhile(_______).doOnNext(elevator::addPassenger).subscribe(mSubscriber);
        assertThat(elevator.getPassengerCount()).isGreaterThan(0);
        assertThat(elevator.getTotalWeight()).isLessThan(Elevator.MAX_CAPACITY_POUNDS);
        assertThat(elevator.getPassengerCount()).isEqualTo(2);
        System.out.println("elevator stats: " + elevator);
        /**
         * One of the great advantages of using RxJava is that functions become composable:
         * we can easily reuse existing pieces of the pipeline by plugging them into other pipelines.
         * takeWhile() accepts a predicate or rule for determining
         */
        elevator.unload();

        elevatorQueueTwo.takeWhile(elevatorRule).subscribe(elevator::addPassenger);
        assertThat(elevator.getPassengerCount()).isGreaterThan(0);
        assertThat(elevator.getTotalWeight()).isLessThan(Elevator.MAX_CAPACITY_POUNDS);
        assertThat(elevator.getPassengerCount()).isEqualTo(2);

        /**
         * a (secret) Extra Challenge!
         * Using what we've learned of rxJava so far, how could we get a list of passengers from elevatorQueueOne that didn't make it
         * into elevatorOne?
         */
        mSubscriber = new TestSubscriber<>();
        //
        // ???
        //
        // assertThat(mSubscriber.getOnNextEvents()).hasSize(3);
    }

    /**
     * Next on our tour, we will see .amb(). Stands for Ambiguous - a somewhat mysterious name (traces its historical roots to the 60')!
     * What it does is it moves forward with the first of a set of Observables to emit an event.
     *
     * Useful in this situation below : 3 servers with the same data, but different response times.
     * Give us the fastest!
     */

    @Test
    public void AmbStandsForAmbiguousAndTakesTheFirstOfTwoObservablesToEmitData() {

        Integer randomInt = new Random().nextInt(100);
        Integer randomInt2 = new Random().nextInt(100);
        Integer randomInt3 = new Random().nextInt(100);

        // bonus - there's a MathObservable object that knows how to do math type things to numbers!
        //
        // here, we're getting the smallest of 3 numbers!
        Integer smallestNetworkLatency = MathObservable.from(Observable.just(randomInt, randomInt2, randomInt3)).min(Integer::compareTo).toBlocking().last();

        Observable<String> networkA = Observable.just("request took : " + randomInt + " millis").delay(randomInt, TimeUnit.MILLISECONDS);
        Observable<String> networkB = Observable.just("request took : " + randomInt2 + " millis").delay(randomInt2, TimeUnit.MILLISECONDS);
        Observable<String> networkC = Observable.just("request took : " + randomInt3 + " millis").delay(randomInt3, TimeUnit.MILLISECONDS);
        /**
         * Do we have several servers that give the same data and we want the fastest of the two?
         */
        Observable.amb(________, ________, ________).subscribe(mSubscriber);
        mSubscriber.awaitTerminalEvent();
        List<Object> onNextEvents = mSubscriber.getOnNextEvents();
        assertThat(onNextEvents).contains("request took : " + ____ + " millis");
        assertThat(onNextEvents).hasSize(1);

        // bonus! we can call .cache() on an operation that takes a while. It will save the pipeline's events
        // up to the point that .cache() was called, saving them for use again.
        // http://reactivex.io/RxJava/javadoc/rx/Observable.html#cache()
        // networkA.cache().first();
    }

    /**
     * The all operator collects everything emitted in the Observable, and then evaluates a predicate,
     * which then emits true or false.
     */

    @Test
    public void checkingEverything() {
        Observable.just(2, 4, 6, 8, 9)
                .all(integer -> integer % 2 == 0)
                .subscribe(aBoolean -> mBooleanValue = aBoolean);
        assertThat(mBooleanValue).isEqualTo(____);
    }

    /**
     * OK, it's time for a challenge!
     * Given the range below and what we've learned of rxjava so far, how can we produce an mSum equal to 19??
     * Hint: There are a couple ways you could do this, but the most readable will involve 2 operations.
     */
    @Test
    public void challenge_compositionMeansTheSumIsGreaterThanTheParts() {
        mSum = 0;
        Observable<Integer> range = Observable.range(1, 10);
        //hmmmmmmmm.. how can we emit 1 value of 19 from the original range of numbers?
        assertThat(mSum).isEqualTo(19);
    }

    /**
     * So far we've dealt with a perfect world. Unfortunately the real world involves exceptions!
     * <p>
     * How do we respond to those exceptions in our program? Fortunately rxJava comes with many ways of handling these problems.
     * Our first means to do this is with the .onError() event we can implement in our pipeline. This will receive whatever
     * exception was emitted, so that we can log about it, take action, or notify the user for example.
     */
    @Test
    public void onErrorIsCalledWhenErrorsOccur() {
        List<String> arrayOne = new ArrayList<>();
        List<String> arrayTwo = new ArrayList<>();
        List<String> arrayThree = null;
        Object mThrowable = null;
        Observable.just(arrayOne, arrayTwo, arrayThree).map(new Func1<List<String>, List<String>>() {
            @Override
            public List<String> call(List<String> strings) {
                strings.add("GOOD JOB!");
                return strings;
            }
        }).doOnError(oops -> ______ = oops).subscribe(mSubscriber);
        assertThat(mThrowable).isInstanceOf(Throwable.class);
    }

    /**
     * In this test, our flaky comcast modem is on the blink again unfortunately.
     * .retry(long numberOfAttempts) can keep resubscribing to an Observable until a different non-error result occurs.
     * http://reactivex.io/documentation/operators/retry.html
     */
    @Test
    public void retryCanAttemptAnOperationWhichFailsMultipleTimesInTheHopesThatItMaySucceeed() {
        Observable<String> networkRequestObservable = Observable.just(new ComcastNetworkAdapter()).map(new Func1<ComcastNetworkAdapter, String>() {
            @Override
            public String call(ComcastNetworkAdapter networkAdapter) {
                return networkAdapter.getData().get(0);
            }
        }).repeat(100);
        networkRequestObservable.retry(____).subscribe(mSubscriber);
        assertThat(mSubscriber.getOnNextEvents().get(0)).isEqualTo("extremely important data");
    }

}
