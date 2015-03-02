import org.junit.Before;
import org.junit.Test;
import rx.Observable;
import rx.functions.Func1;
import rx.observers.TestSubscriber;
import util.LessonResources.CarnivalFood;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static util.LessonResources.ElevatorPassenger;

public class lessonB_Solutions {

    private String _____;
    private int ____;
    private Object ______ = "";
    public String mStringA;
    public String mStringB;
    private TestSubscriber<Object> mSubscriber;
    private List<Observable<Observable>> mField;

    @Before
    public void setup() {
        mSubscriber = new TestSubscriber<>();
    }

    /**
     * the Map function transforms the items emitted by an Observable by applying a function to each, changing the content.
     */
    @Test
    public void mapAppliesAFunctionToEachItemAndEmitsDataOnTheOtherSide() {
        Observable.from(Arrays.asList("kewl", "leet", "speak"))
                .map(word -> word.replace("e", "3"))
                .map(word -> word.replace("l", "1"))
                .subscribe(mSubscriber);
        assertThat(mSubscriber.getOnNextEvents()).contains("k3w1");
        assertThat(mSubscriber.getOnNextEvents()).contains("133t");
        assertThat(mSubscriber.getOnNextEvents()).contains("sp3ak");
    }

    /**
     * Understanding what flatMap() does is a major awakening on the seeker's path to rx enlightenment.
     * We will use non-lambda syntax here to help illustrate what the return types are in this use case for flatmap.
     * For this experiment, we will be going to the carnival. Because we spent our money unwisely at the
     * carnival ($25 dollars on the Dunk Tank), we are left only with 5$.
     * We still need to eat though. Our goal - check the available food options and get a filtered list of things under 5$.
     */

    @Test
    public void flatMapUnwrapsOneLevelOfNestingInAnObservableStream() {
        /**
         * The First Food cart's offerings:
         */
        List<CarnivalFood> funnelCakeCart = Arrays.asList(new CarnivalFood("Cheese Pizza", 5.95),
                new CarnivalFood("Funnel Cake", 3.95),
                new CarnivalFood("Candied Apple", 1.50),
                new CarnivalFood("Jumbo Corn Dog", 2.25),
                new CarnivalFood("Deluxe Corned Beef Hoagie with Swiss Cheese", 6.75),
                new CarnivalFood("Faygo", 1.95));
        /**
         * The Second Food Cart's offerings:
         */
        List<CarnivalFood> chineseFoodCart = Arrays.asList(new CarnivalFood("Duck Teriyaki Kabobs", 12.95),
                new CarnivalFood("Vegetable Dumplings", 2.50),
                new CarnivalFood("Poor Quality Shrimp Lo Mein", 4.75),
                new CarnivalFood("Green Tea Ice Cream", 3.95),
                new CarnivalFood("Basic Mandarin Chicken", 5.25));

        /**
         * Emit each foodCart list on a stream.
         */
        Observable<List<CarnivalFood>> foodCartItemsObservable = Observable.just(funnelCakeCart, chineseFoodCart);

        /**
         *  what do you think calling .map() on the foodCartItemsObservable will do?
         */
        Observable<Observable<CarnivalFood>> map = foodCartItemsObservable.map(new Func1<List<CarnivalFood>, Observable<CarnivalFood>>() {
            @Override
            public Observable<CarnivalFood> call(List<CarnivalFood> foods) {
                Observable<CarnivalFood> from = Observable.from(foods);
                return from;
            }
        });
        map.subscribe(mSubscriber);

        assertThat(mSubscriber.getOnNextEvents()).hasSize(2);

        /** Was the result above what you expected? A bit strange huh? You'd think that you'd get
         * a value matching the number of items of foods in each list at first glance.
         * The reason we get a different result is because of the difference between map(), and flatmap(), which we will see next.
         * map() will always keep the SAME NUMBER OF events/ data as the previous segment in the pipeline. It can never change the number
         * of items on the previous piece of the pipeline.

         * Next, we would like to begin filtering the list to match what we can afford to eat.
         * The problem now is that rather than Observable<Food> items, we are emitting Observable<Observable<Food>>s instead.
         * We can't filter these, because Observable has no price (its content does, but we cant access that).
         * This is where flatMap comes in!
         */

        mSubscriber = new TestSubscriber<>();
        /**
         * flatMap() transform the items emitted by an Observable into Observables, then flattens the emissions from those into a single Observable
         * As martin fowler defines flatMap:
         * Map a function over a collection and flatten the result by one-level. In this case, we will map a function over the list of List<Food>s
         * and then flatten them into one list.
         */
        Observable<CarnivalFood> individualItemsObservable = foodCartItemsObservable.flatMap(new Func1<List<CarnivalFood>, Observable<CarnivalFood>>() {
            @Override
            public Observable<CarnivalFood> call(List<CarnivalFood> foods) {
                return Observable.from(foods);
            }
        });
        individualItemsObservable.subscribe(mSubscriber);
        assertThat(mSubscriber.getOnNextEvents()).hasSize(11);

        mSubscriber = new TestSubscriber<>();

        /**
         * Now that the answer to the riddle of flatMap has been revealed to us, we may filter the stream of
         * individual carnival food items and eat what we can afford. to do that we can use the
         * filter() operator.
         * public final Observable<T> filter(Func1<? super T,java.lang.Boolean> predicate)
         * if the predicate returns true, the data/event being evaluated in the predicate is passed on
         */
        individualItemsObservable.filter(new Func1<CarnivalFood, Boolean>() {
            @Override
            public Boolean call(CarnivalFood food) {
                return food.mPrice < 5.00;
            }
        }).subscribe(mSubscriber);

        assertThat(mSubscriber.getOnNextEvents()).hasSize(7);

        System.out.println("With my 5 bucks I can buy: " + mSubscriber.getOnNextEvents());
    }

    /**
     * Reduce is helpful for aggregating a set of data and emitting a final result
     */
    @Test
    public void theReduceOperatorAccumulatesValuesAndEmitsTheResult() {

        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();

        List<ElevatorPassenger> elevatorPassengers = Arrays.asList(
                new ElevatorPassenger("Max", 168),
                new ElevatorPassenger("Mike", 234),
                new ElevatorPassenger("Ronald", 192),
                new ElevatorPassenger("William", 142),
                new ElevatorPassenger("Jacqueline", 114));
        Observable<ElevatorPassenger> elevatorPassengersObservable = Observable.from(elevatorPassengers);
        /**
         * http://reactivex.io/documentation/operators/reduce.html
         */
        elevatorPassengersObservable.reduce(0, (accumulatedWeight, elevatorPassenger) ->
                elevatorPassenger.mWeightInPounds += accumulatedWeight)
                .subscribe(testSubscriber);
        assertThat(testSubscriber.getOnNextEvents().get(0)).isEqualTo(850);
    }

    /**
     * .repeat() creates an Observable that emits a particular item or sequence of items repeatedly
     */
    @Test
    public void repeatOperatorRepeatsThePreviousOperationANumberOfTimes() {
        String weapon = "A Boomerang made of Pure Gold";
        TestSubscriber<Object> subscriber = new TestSubscriber<>();

        Observable<String> repeatingObservable = Observable.just(weapon).repeat(4);
        repeatingObservable.subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents()).hasSize(4);

        subscriber = new TestSubscriber<>();
        /**
         * Challenge - what about this one?? Remember, .repeat() repeats the previous step in the pipeline
         */
        Observable<String> challengeRepeatingObservable = repeatingObservable.repeat(4);
        challengeRepeatingObservable.subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents()).hasSize(16);
    }

    /**
     * A great feature of RxJava is that we can chain actions together to achieve more functionality.
     * In this example we have one Observable and we perform two actions on the data it emits.
     * Lets build two Strings by concatenating some integers.
     */
    @Test
    public void composableFunctions() {
        mStringA = "";
        mStringB = "";
        Observable.range(1, 6)
                .doOnNext(integer -> mStringA += integer)
                .doOnNext(integer -> {
                    if (integer % 2 == 0) {
                        mStringB += integer;
                    }
                })
                .subscribe();
        assertThat(mStringA).isEqualTo("123456");
        assertThat(mStringB).isEqualTo("246");
    }

    /**
     * Instead of just using events as input to actions (for example summing them), we can transform the events themselves.
     * We'll use the map() function for this. Lets take some text and map it to all lowercase. The key to making this work is to
     * return the same variable that comes into the action.
     */
    @Test
    public void convertingEvents() {
        mStringA = "";
        Observable.just("wE", "hOpe", "yOU", "aRe", "eNjOyInG", "thIS")
                .map(s -> s.toLowerCase())
                .subscribe(s -> mStringA += s + " ");

        assertThat(mStringA).isEqualTo("we hope you are enjoying this ");
    }


}
