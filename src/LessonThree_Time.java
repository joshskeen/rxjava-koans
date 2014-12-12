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

    @Test
    public void launchingAnActionInTheFuture() throws InterruptedException {
        final long delay = 2000;
        Schedulers.newThread().createWorker().schedule(() -> mReceived = "Finished", delay, TimeUnit.MILLISECONDS);
        Thread.sleep(_____);
        assertThat(mReceived).isEqualTo("Finished");
    }

    @Test
    public void launchingAnEventInTheFuture() throws InterruptedException {
        mReceived = "";
        mDelay = 2000;
        Observable.just("Godot").delay(mDelay, TimeUnit.MILLISECONDS).doOnNext(s -> mReceived = s).subscribe();
        Thread.sleep(_____);
        assertThat(mReceived).isEqualTo("Godot");
    }

    @Test
    public void aWatchedPot() throws InterruptedException {
        mReceived = "";
        mDelay = 2000;
        Observable<String> mFallback = Observable.just("Tepid");
        Observable.just("Boiling")
                .delay(_____, TimeUnit.MILLISECONDS)
                .timeout(mDelay, TimeUnit.MILLISECONDS, mFallback)
                .subscribe(s -> mReceived = s);
        Thread.sleep(mDelay);
        assertThat(mReceived).isEqualTo("Tepid");
    }

}
