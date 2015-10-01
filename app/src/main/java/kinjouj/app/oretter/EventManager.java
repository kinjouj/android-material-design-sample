package kinjouj.app.oretter;

import de.greenrobot.event.EventBus;

import static kinjouj.app.oretter.AppInterfaces.AppEvent;

public class EventManager {

    public static void register(Object o) {
        EventBus eventBus = EventBus.getDefault();
        eventBus.register(o);
    }

    public static void unregister(Object o) {
        EventBus.getDefault().unregister(o);
    }

    public static void post(AppEvent event) {
        EventBus.getDefault().post(event);
    }
}
