package kinjouj.app.oretter;

import java.util.List;

import android.content.Context;

public interface AppInterfaces {

    public static interface AppEvent {
        void run(Context context);
    }

    public static interface OnLoadCallback {
        void run(Throwable t);
    }

    public static interface SortedListAdapter<T> {
        void add(T value);
        void addAll(List<T> values);
    }
}
