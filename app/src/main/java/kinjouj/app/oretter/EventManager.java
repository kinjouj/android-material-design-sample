package kinjouj.app.oretter;

import de.greenrobot.event.EventBus;

import kinjouj.app.oretter.AppInterfaces;

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
