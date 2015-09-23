package kinjouj.app.oretter;

import android.content.Context;
import de.greenrobot.event.EventBus;

import static kinjouj.app.oretter.AppInterfaces.AppEvent;

public class EventHandler {

    public static void register(Object o) {
        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(o)) {
            eventBus.register(o);
        }
    }

    public static void unregister(Object o) {
        EventBus.getDefault().unregister(o);
    }

    public static void post(AppEvent event) {
        EventBus.getDefault().post(event);
    }
}
