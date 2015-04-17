package yurezcv.cv.coffeeapp.bus;

import com.squareup.otto.Bus;

public final class BusProvider {

    private static final EventBus BUS = new EventBus();

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
        // No instances.
    }
}