import org.junit.Test;
import rx.Observable;
import rx.observables.MathObservable;
import rx.observables.StringObservable;

import static org.fest.assertions.Assertions.assertThat;

public class LessonTwo_ComposableObservations {

    public int mSum;
    public String mStringA;
    public String mStringB;
    public Boolean mBooleanValue;
    private Object _____;
    private int ____;

    /*
    So far, each of our observables only did one thing; they only had one bit of code inside either subscribe() or doOnNext().
    However the real power of reactive functional programming comes from that fact that we can combine functions together
    to do multiple things to our items. We say that we are "composing sequences". There are two ways to do this. Lets look at the first way:

    We can pass one observable as an input to another. Here we are going to create an Observable of Integers and then pass
    it to a second Observable: special MathObservable which will sum the integers.
    */
    @Test
    public void composableObservables() {
        mSum = 0;
        Observable<Integer> numbers = Observable.just(10, 100, ____);
        MathObservable.sumInteger(numbers).subscribe(integer -> mSum = integer);
        assertThat(mSum).isEqualTo(1110);
    }

    /*
    The second way we can compose sequences is with a fluent style. We can chain methods together to achieve more functionality.
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

    @Test
    public void weWroteThis() {
        mStringA = "";
        int ____ = 0;
        StringObservable.join(Observable.just("Josh", "Bill", "Joe", "Fredric")
                .filter(s -> s.length() <= ____), ",").subscribe(s -> mStringA = s);
        assertThat(mStringA).isEqualTo("Josh,Bill,Joe");
    }

    @Test
    public void convertingEvents() {
        mStringA = "";
        Observable.just("wE", "hOpe", "yOU", "aRe", "eNjOyInG", "thIS").map(s -> _____).subscribe(s -> mStringA += s + " ");
        assertThat(mStringA).isEqualTo("we hope you are enjoying this ");
    }

    @Test
    public void creatingAMoreRelevantEventStream() {
        mStringA = "";
        final int windowTopX = 50;
        Observable.just(100, 200, 150).map(integer -> integer - ____).subscribe(integer -> mStringA += integer + ", ");
        assertThat(mStringA).isEqualTo("50, 150, 100, ");
    }

    @Test
    public void checkingEverything() {
        mSum = 0;
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
