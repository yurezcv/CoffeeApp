package yurezcv.cv.coffeeapp.bus;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

public class EventBus extends Bus {

    /**
     * Extension of EventBus Otto library. This extension allows to post events
     * from any thread, not only from the main thread.
     */
    private final Handler mMainThread = new Handler(Looper.getMainLooper());

    /**
     * Do not call this constructor directly! Instead use dependency injection
     * to obtain the single instance of EventBus. EventBus must be a singleton
     * because different instances will never share their events.
     */
    EventBus() {

        // No instances outside this package.
    }

    /**
     * The single, thread-safe method to post events to the EventBus.
     *
     * @param event Event to post to the EventBus.
     */
    @Override
    public void post(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event);
        } else {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    post(event);
                }
            });
        }
    }
}
