package kinjouj.app.oretter;

import de.greenrobot.event.EventBus;

public class EventManager {

    public static EventBus getInstance() {
        return EventBus.getDefault();
    }

    public static void register(Object o) {
        getInstance().register(o);
    }

    public static void unregister(Object o) {
        getInstance().unregister(o);
    }
}
