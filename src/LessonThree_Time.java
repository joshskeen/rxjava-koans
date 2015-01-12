import org.junit.Test;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

import static org.fest.assertions.Assertions.assertThat;

public class LessonThree_Time {

    public String mReceived = "";
    public long mDelay;
    public long mTimeout;
    public long _____;


    /*
    So far all of our work has been performed pretty much immediately. We were able to delay an observable by keeping it "cold"
    and only subscribing to it when we needed it. However, we can also add time delays directly into our pipeline. The first way is to
    delay an action. We are going to use a Scheduler object to do this. We'll see more about Schedulers when we learn about multithreading.
    For now we can use a Scheduler to schedule an Action on our main thread. Lets schedule an action and then sleep for a while.
     */
    @Test
    public void launchingAnActionInTheFuture() throws InterruptedException {
        final long delay = 2000;
        Schedulers.immediate().createWorker().schedule(() -> mReceived = "Finished", delay, TimeUnit.MILLISECONDS);
        Thread.sleep(_____);
        assertThat(mReceived).isEqualTo("Finished");
    }

    /*
    We can also delay individual events by adding a delay as an action in our pipeline. Lets do that here.
     */
    @Test
    public void launchingAnEventInTheFuture() throws InterruptedException {
        mReceived = "";
        final long delay = 2000;
        Observable.just("Godot")
                .delay(delay, TimeUnit.MILLISECONDS)
                .doOnNext(s -> mReceived = s)
                .subscribe();

        Thread.sleep(_____);
        assertThat(mReceived).isEqualTo("Godot");
    }

    /*
    Anytime we are dealing with timing we'll want to handle the possibility of timeout. In our prior examples we dictated the timing
    by using some delay. But we might have a situation where the timing is outside of our control (that's one of the main benefits of
    asynchronicity with RXJava). Here we are faking some unexpected delay. Lets catch this timeout and print the correct string.
    */
    @Test
    public void aWatchedPot() throws InterruptedException {
        mReceived = "";
        final long unexpectedDelay = 2000;
        final long timeoutDuration = _____;

        Observable<String> mFallback = Observable.just("Tepid");

        Observable.just("Boiling")
                .delay(unexpectedDelay, TimeUnit.MILLISECONDS)
                .timeout(timeoutDuration, TimeUnit.MILLISECONDS, mFallback)
                .subscribe(s -> mReceived = s);

        Thread.sleep(unexpectedDelay);

        assertThat(mReceived).isEqualTo("Tepid");
    }
}
