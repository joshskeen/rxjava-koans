import org.junit.Test;
import rx.Observable;
import rx.functions.Action1;
import rx.observables.MathObservable;

import static org.fest.assertions.Assertions.assertThat;

public class LessonTwo_ComposableObservations {

    public int mSum;
    public String mStringA;
    public String mStringB;
    public Boolean mBooleanValue;

    // You don't have to mess with these. They are for making tests compile.
    private Object _____;
    private int ____;
    //


    /*
    So far, each of our observables only did one thing; they only had one bit of code inside either subscribe() or doOnNext().
    Each of these things was an "action". An action is the bit of code to be performed. We can see an action more clearly if we expand the lambdas
    we've been using so far.

    Lets add up some numbers. See that our Action1 is the part doing the work.
     */
    @Test
    public void actionJackson() {
        mSum = 0;
        Observable.just(9, 90, 900)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        mSum += integer;
                    }
                });

        assertThat(mSum).isEqualTo(____);
    }

    /*
    However the real power of reactive functional programming comes from that fact that we can combine actions together
    to do multiple things to our events. We say that we are "composing sequences". There are two ways to do this. Lets look at the first way:

    We can pass one observable as an input to another. Here we are going to create an Observable of Integers and then pass
    it to a second Observable: special MathObservable which will sum the integers.
    */
    @Test
    public void composableObservables() {
        mSum = 0;
        Observable<Integer> numbers = Observable.just(10, 100, ____);
        MathObservable.sumInteger(numbers)
                .subscribe(integer -> {
                    mSum = integer;
                });
        assertThat(mSum).isEqualTo(1110);
    }

    /*
    The second way we can compose sequences is with a fluent style. We can chain actions together to achieve more functionality.
    In this example we have one Observable and we "sequentially" perform two actions. Lets build two Strings by concatenating some integers.
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

        assertThat(mStringA).isEqualTo("____");
        assertThat(mStringB).isEqualTo("____");
    }

    /*
    In the previous test our second doOnNext() method was really doing two things: it was checking for even numbers and concatenating a string.
    The beauty of functional programming is that we can break each little bit of functionality into its own pipe in the pipeline.

     So far we've added functionality to our pipeline in two places: subscribe() and doOnNext(). Both of these are nice and generic but
     RXJava provides us with a plethora of specialty methods. Lets use one of those methods, called filter() to check for Strings of a certain length.
     */
    @Test
    public void weWroteThis() {
        mStringA = "";

        Observable.just("Josh", "Bill", "Joe", "Fredric")
                .filter(s -> s.length() <= ____)
                .doOnNext(s -> mStringA = mStringA + s)
                .subscribe();

        assertThat(mStringA).isEqualTo("JoshBillJoe");
    }

    /*
    Instead of just using events as input to actions (for example summing them), we can transform the events themselves.
    We'll use the map() function for this. Lets take some text and map it to all lowercase. The key to making this work is to
    return the same variable that comes into the action.
     */
    @Test
    public void convertingEvents() {
        mStringA = "";
        Observable.just("wE", "hOpe", "yOU", "aRe", "eNjOyInG", "thIS")
                .map(s -> _____)
                .subscribe(s -> mStringA += s + " ");

        assertThat(mStringA).isEqualTo("we hope you are enjoying this ");
    }

    /*
    So far we have performed operations on each event coming through the pipe. However, we can also perform operations
    on all the events together that have so far come through the pipeline. Lets make sure that every time we get a new integer
    we check that all integers so far are even numbers.
    */
    @Test
    public void checkingEverything() {
        Observable.just(2, 4, 6, 8)
                .all(integer -> integer % 2 == 0)
                .subscribe(aBoolean -> mBooleanValue = aBoolean);
        assertThat(_____).isEqualTo(____);
    }

    /* OK time for a challenge!
    We've given you a starting stream and an assertion that needs to pass. Take what you learned in this lesson to make it work.
    You're free to use some combination of chained (i.e. fluent) or nested functionalities.
    */
    @Test
    public void challenge_compositionMeansTheSumIsGreaterThanTheParts() {
        Observable.range(1, 10);

        // ___

        assertThat(mSum).isEqualTo(19);
    }
}
